import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World extends JFrame implements MouseListener,KeyListener,TreeSelectionListener,ActionListener,MouseMotionListener {
   // public JFrame f = new JFrame("World");
    public JFrame ff = new JFrame("TreeFrame");
    public RootObject rootObject = new RootObject();
    private VectorObject selectedNode;
    private JTree tree;
    private JScrollPane jSP;
    private String currentVectorType = "Circle";
    private String currentTransformType = "Translate";
    private JMenuBar menuBar = new JMenuBar();
    private JMenu vectorMenu = new JMenu("Add Object");
    private  JMenuItem circleMenuItem = new JMenuItem("Circle");
    private  JMenuItem lineMenuItem = new JMenuItem("Cube");
    private  JMenuItem rectMenuItem = new JMenuItem("Rect");
    private  JMenu transMenu = new JMenu("Transform");
    private  JMenuItem translateMenuItem = new JMenuItem("Translate");
    private  JMenuItem scaleMenuItem = new JMenuItem("Scale");
    private  JMenuItem rotateMenuItem = new JMenuItem("Rotate");

    private Screen screen;
    private int width,height;
    private BufferedImage image,bitMap;
    private int[] pixels;
    private boolean gameRunning;
    private int fps,lastFpsTime;
    public double a=0;

    public List<Object3D> object3DList = new ArrayList<Object3D>();
    public Object3D bunny;
    public Object3D ray;
    public List<Object3D> rayList = new ArrayList<Object3D>();
    public int activeObject;
    public boolean calculated = false;
    public SymmetryDetector detector = new SymmetryDetector();
    public BufferedImage obs;
    public Color drawColor;
    public int symmetricParts = 1;
    public int rotation = 0;
    public int[] myFunction;
    public int stauchFaktor = 10;
    public int zugKraft = 2;
    public double [] dftReell;
    public double [] dftKomplex;
    public double [] Ak;
    public boolean drawDFT = false;

    public World() throws IOException {

        circleMenuItem.addActionListener(this);
        lineMenuItem.addActionListener(this);
        rectMenuItem.addActionListener(this);
        translateMenuItem.addActionListener(this);
        scaleMenuItem.addActionListener(this);
        rotateMenuItem.addActionListener(this);
        vectorMenu.add(circleMenuItem);
        vectorMenu.add(lineMenuItem);
        vectorMenu.add(rectMenuItem);
        transMenu.add(translateMenuItem);
        transMenu.add(scaleMenuItem);
        transMenu.add(rotateMenuItem);
        menuBar.add(vectorMenu);
        menuBar.add(transMenu);
        ff.setJMenuBar(menuBar);
        this.setSize(800, 700);
        width=this.getWidth();
        height=this.getHeight();
        System.out.println(width+"----"+height);
//        this.createBufferStrategy(2);
        screen = new Screen(width,height);
        image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        drawColor = new Color(255,0,0);
        ff.setSize(100, 700);
        ff.setLocation(0,0);
        this.setLocation(100,0);
            //f.setLayout(new BorderLayout());
            //this.setLayout(new BorderLayout());
           // CircleObject co = new CircleObject(100,100,100,100);
           // co.isSelected();
           // rootObject.addObject(co );
            tree = new JTree(rootObject);
            //tree.setSelectionModel(new TreeSelectionModel() {
            //});

        tree.addTreeSelectionListener(this);

        //Dimension minimumSize = new Dimension(400, 400);
        //this.setMinimumSize(minimumSize);
        jSP = new JScrollPane(tree);


        /*this.getInputMap().put(KeyStroke.getKeyStroke('a'),"a");
        this.getActionMap().put("a", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('d'),"d");
        this.getActionMap().put("d", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('w'),"w");
        this.getActionMap().put("w", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('s'),"s");
        this.getActionMap().put("s", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('q'),"q");
        this.getActionMap().put("q", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('e'),"e");
        this.getActionMap().put("e", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('r'),"r");
        this.getActionMap().put("r", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('t'),"t");
        this.getActionMap().put("t", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('f'),"f");
        this.getActionMap().put("f", keyAction);
        this.getInputMap().put(KeyStroke.getKeyStroke('g'),"g");
        this.getActionMap().put("g", keyAction);*/

       // jSP.addKeyListener(this);
        //this.add(this);
        //f.addKeyListener(this);
        ff.add(jSP);
       // f.add( jSP,BorderLayout.WEST );
        //f.add(this,BorderLayout.CENTER);
        ff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ff.setVisible(true);

        this.addKeyListener(this);
        //ff.addKeyListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        //f.addKeyListener(this);
           // f.pack();
        //addWall();
      //bunny = new Object3D(this.width,this.height);
        //bunny.loadFile("src/bun_zipper_res3.ply");
        //bunny.loadFile("src/sphere.ply");
        //bunny.scale(0.6,0.6,0.6);
        //bunny.translate(0,0,-10);
        //bunny.movingX = 0.02;
        //bunny.movingY = -0.02;*/

     //   ray = new Object3D(this.width,this.height);


        addObject();
        //object3DList.get(object3DList.size()-1).scale(2,2,2);
       // addObject();
        //object3DList.add(bunny);
        //activeObject=2;
        /*bunny = new Object3D(this.width,this.height);
        bunny.loadFile("src/bun_zipper_res3.ply");
        //bunny.loadFile("src/sphere.ply");
        bunny.scale(10,10,10);
        bunny.translate(0,0,-10);
        //bunny.movingX = 0.2;
        //bunny.movingY = -0.2;
        object3DList.add(bunny);
        activeObject=3;*/
        //object3DList.add(new Object3D(this.width,this.height));
        //object3DList.get(0).addObject();
        //addRay(0,0);
        bitMap = ImageIO.read(new File("src/Bitmap2.jpg"));
        //detector.setScoreThreshold(0.9f);
        //obs = new BufferedImage(bitMap.getWidth(), bitMap.getHeight(), BufferedImage.TYPE_INT_RGB);
        //detector.setObservation(obs.createGraphics());
//run the detector - symmetries are available from the returned object
       // SymmetryDetector.Symmetry symmetry = detector.detectSymmetry(bitMap);
//the image obs will contain a graphical summary of execution
        //bitMap = obs;
        myFunction = new int[width];
        dftReell = new double[myFunction.length];
        dftKomplex = new double[myFunction.length];
        Ak = new double[myFunction.length];
        for(int i = 0;i<myFunction.length;i++){
            myFunction[i]=height/2 - 20;
        }
        activeObject=object3DList.size()-1;
        gameRunning=true;

        gameLoop();
        //bitMap = new BufferedImage();


    }
    public void blatt4(Graphics2D g2d){
       // this.setSize(bitMap.getWidth(),bitMap.getHeight());
        //g2d.drawImage(bitMap,0,0,null);
        /*List<Integer> colorList = new ArrayList<>();
        if(!calculated) {
            int counter = 0;

            for (int i = 0; i < bitMap.getWidth(); i++) {
                for (int j = 0; j < bitMap.getHeight(); j++) {
                    if(!colorList.contains(bitMap.getRGB(i,j)/400000)){
                        colorList.add(bitMap.getRGB(i,j)/400000);
                        counter++;
                    }

                }
            }
            System.out.println(counter+" ---- "+counter/(bitMap.getWidth()*bitMap.getHeight()));
            calculated = true;
        }*/
        //Blatt 5
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0,height/2,width,height/2);
        g2d.drawLine(width/2,0,width/2,height);
        g2d.setColor(Color.RED);
        for(int i = 0;i<myFunction.length-1;i++){
            g2d.drawLine(i,myFunction[i],i+1,myFunction[i+1]);
        }
        g2d.drawString("Stauchfaktor: "+stauchFaktor,10,50);
        g2d.drawString("Zugkraft: 1/"+zugKraft,10,80);

        if(drawDFT){
            System.out.println("ok");
            g2d.setColor(Color.ORANGE);
            for(int i=0;i<width-1;i++){
                g2d.drawLine(i,(int)Ak[i],i+1,
                        (int)Ak[i+1]);
                g2d.drawLine(i,(int)((dftReell[i])),i+1,
                        (int)((dftReell[i+1])));
            }
        }
    }
    public void gameLoop()
    {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        // keep looping round til the game ends
        while (gameRunning)
        {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            // update the frame counter
            lastFpsTime += updateLength;
            fps++;

            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= 1000000000)
            {
               // System.out.println("(FPS: "+fps+")");
                lastFpsTime = 0;
                fps = 0;
            }

            // update the game logic
            doGameUpdates(delta);

            // draw everyting
            render();
            //do
            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give
            // us our final value to wait for
            // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
            /*try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void doGameUpdates(double delta)
    {
        for(Object3D obj:object3DList) {
            obj.rotate(0, 0.1 * delta * obj.rotatingY, 0.1 * delta * obj.rotatingZ);
            obj.translate(0.1 * delta * obj.movingX, 0.1 * delta * obj.movingY, 0.1 * delta * obj.movingZ);
        }
        a+=Math.PI/100;

    }
    private void addWall(){
        object3DList.add(new Object3D(this.width,this.height));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,1,-0.1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,-1,-0.1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,-1,-50));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,1,-50));
        object3DList.get(object3DList.size()-1).addP(0,1,2,Color.ORANGE);
        object3DList.get(object3DList.size()-1).addP(2,3,0,Color.ORANGE);
        object3DList.get(object3DList.size()-1).ray=true;
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,1,-0.1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,-1,-0.1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,-1,-50));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,1,-50));
        object3DList.get(object3DList.size()-1).addP(4,7,6,Color.ORANGE);
        object3DList.get(object3DList.size()-1).addP(6,5,4,Color.ORANGE);

        object3DList.get(object3DList.size()-1).addP(0,3,7,Color.BLUE);
        object3DList.get(object3DList.size()-1).addP(7,4,0,Color.BLUE);

        object3DList.get(object3DList.size()-1).addP(1,5,6,Color.GREEN);
        object3DList.get(object3DList.size()-1).addP(6,2,1,Color.GREEN);
        //activeObject=object3DList.size()-1;
    }
    private void addObject(){
        object3DList.add(new Object3D(this.width,this.height));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,-1,-1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,1,-1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,1,-1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,-1,-1));

        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,-1,1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,-1,1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(1,1,1));
        object3DList.get(object3DList.size()-1).addV(new Vertex(-1,1,1));
        Color c = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1.f);
        object3DList.get(object3DList.size()-1).addP(4,6,7,c);
        object3DList.get(object3DList.size()-1).addP(4,5,6,c);
        c = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1.f);
        object3DList.get(object3DList.size()-1).addP(0,1,2,c);
        object3DList.get(object3DList.size()-1).addP(2,3,0,c);
        c = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1.f);
        object3DList.get(object3DList.size()-1).addP(0,4,7,c);
        object3DList.get(object3DList.size()-1).addP(7,1,0,c);
        c = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1.f);
        object3DList.get(object3DList.size()-1).addP(6,5,3,c);
        object3DList.get(object3DList.size()-1).addP(3,2,6,c);
        c = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1.f);
        object3DList.get(object3DList.size()-1).addP(0,3,5,c);
        object3DList.get(object3DList.size()-1).addP(5,4,0,c);
        c = new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1.f);
        object3DList.get(object3DList.size()-1).addP(7,6,1,c);
        object3DList.get(object3DList.size()-1).addP(1,6,2,c);

        object3DList.get(object3DList.size()-1).scale(0.30,0.30,0.30);
        object3DList.get(object3DList.size()-1).translate(0,0,-10);

        activeObject=object3DList.size()-1;
    }
    private void addRay(double x,double y){
        ray = new Object3D(this.width,this.height);
        x=2*x/width;
        y=2*y/height;
        //System.out.println(x+ " "+-y);
        ray.addV(new Vertex(0,0,-0.1));
        ray.addV(new Vertex(x,-y,-50));
       // ray.addV(new Vertex(0,0,0));
        //ray.addV(new Vertex(0,0,-0.1));

        ray.ray = true;
        ray.wire=true;
       // Color c = new Color.RED;
        ray.addP(1,0,1,Color.RED);

        for(Object3D obj:object3DList){
            obj.setRay(x,-y,-50);
        }
        rayList.add(ray);
        // object3DList.get(object3DList.size()-1).scale(50,50,50);


    }
    private void addRayBeam(){
        ray = new Object3D(this.width,this.height);
        /*ray.addV(new Vertex(0,0,0));
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                ray.addV(new Vertex((((double)i-width/2))*2/width,
                        (((double)j-height/2))*2/height,-50));
                ray.addP(0,
                        ray.vL.size()-1,0, new Color(1.f,0.f,0.f,1.f));
            }

        }
        ray.wire=true;
        ray.ray=true;*/
        ray.createRayBeam();
        rayList.add(ray);
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;

        }
        //screen.clear();
        //screen.render();

        /*for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }*/

        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        /*for(int x = 300;x<800;x++){
            for(int y = 200;y<500;y++){
            image.setRGB(x,y,Color.RED.getRGB());
            }
        }*/
        /*Polygon p = new Polygon();
        p.addPoint(500,500);
        p.addPoint(400,600);
        p.addPoint(700,700);
        object3DList.get(0).rasterizePolygon(p,image, Color.CYAN,g2d);*/
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        for(int x=0;x<getWidth();x++){
            for(int y=0;y<getHeight();y++){
                image.setRGB(x,y,Color.WHITE.getRGB());
            }
        }

        /*for(int x = 100;x<800;x++){
            for(int y = 200;y<500;y++){
                image.setRGB(x,y,Color.GREEN.getRGB());
            }
        }*/

       /* rootObject.draw(g2d);
        for(Object3D obj:object3DList) {
            //g2d.rotate(a);
            obj.render(g2d,image);
            for(int i=0;i<obj.rayQ.size();i++) {
                g2d.drawString("S: " + obj.rayQ.get(i).getTx() + " " +
                        obj.rayQ.get(i).getTy() + " " +
                        obj.rayQ.get(i).getTz(), 0, (i+1)*25+25);
            }
        }
        for (Object3D obj:rayList) {
            obj.render(g2d, image);

        }
        */
       blatt4(g2d);
        //rayList.get(rayList.size()-1).render(g2d,image);
        bs.show();
        g2d.dispose();
    }
    private void doDrawing(Graphics g) {
        //render();
        Graphics2D g2d = (Graphics2D) g.create();

        /*g2d.setColor(new Color(150, 150, 150));
        g2d.fillRect(20, 20, 80, 50);

        AffineTransform tx1 = new AffineTransform();
        tx1.translate(110, 22);
        tx1.scale(0.5, 0.5);

        g2d.setTransform(tx1);
        g2d.fillRect(0, 0, 80, 50);

        AffineTransform tx2 = new AffineTransform();
        tx2.translate(170, 20);
        tx2.scale(1.5, 1.5);

        g2d.setTransform(tx2);
        g2d.fillRect(0, 0, 80, 50);*/
        //co.draw(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
       // rootObject.draw(g2d);
        //g2d.translate((int)(this.getWidth()/2),(int)(this.getHeight()/2));
        //obj.drawObject(g2d);
      //  obj.render(g2d);
        g2d.dispose();
        //this.repaint();

    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d =(Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
       // super.paintComponent(g);
        //doDrawing(g);
    }
//    @Override
//    public void paint(Graphics g){
//        super.paint(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        co.draw(g2d);
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*TreePath selectedPath = tree.getSelectionPath();
        selectedNode = (VectorObject) selectedPath.getLastPathComponent();
        if(currentVectorType.equals("Circle"))
            selectedNode.addObject(new CircleObject(e.getX(),e.getY(),
                (int)Math.round(Math.random()*200),(int)Math.round(Math.random()*200)));
            else if(currentVectorType.equals("Line"))
                selectedNode.addObject(new LineObject(e.getX(),e.getY(),(int)Math.round(Math.random()*200),
                        (int)Math.round(Math.random()*200)));
        else if(currentVectorType.equals("Rect"))
            selectedNode.addObject(new RectObject(e.getX(),e.getY(),(int)Math.round(Math.random()*200),
                    (int)Math.round(Math.random()*200)));

        //this.repaint();
        //jSP.repaint();
        this.repaint();*/
        for(Object3D obj:object3DList){
            obj.lightSource.setX((((double)(e.getX())-width/2)*2/width));
            obj.lightSource.setY((((double)(e.getY())-height/2)*2/height)*-1);
            obj.lightSource.setZ(-0.1);
            obj.setLightSourceScalar();
            obj.lightColor = Color.YELLOW;
           // System.out.println(obj.lightSource.getX()+" "+obj.lightSource.getY()+" "+obj.lightSource.getZ());
        }
        addRay((double)e.getX()-width/2,(double)e.getY()-height/2);
        /*for(int i=0;i<width;i++){
            for(int j = 0;j<height;j++){
                addRay(i-width/2,j-height/2);
            }
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getY()<myFunction[e.getX()]){
            int d = (myFunction[e.getX()]-e.getY())/zugKraft;
            int mid = e.getX();
            int x1 = -(int)Math.sqrt(d*stauchFaktor)+mid;
            int x2 = (int)Math.sqrt(d*stauchFaktor)+mid;
            for(int i = x1;i<=x2;i++){
                myFunction[i]-= -((i-mid)*(i-mid)/stauchFaktor)+d;
            }

        }else{
            int d = (e.getY()-myFunction[e.getX()])/zugKraft;
            int mid = e.getX();
            int x1 = -(int)Math.sqrt(d*stauchFaktor)+mid;
            int x2 = (int)Math.sqrt(d*stauchFaktor)+mid;
            for(int i = x1;i<=x2;i++){
                myFunction[i]+= -((i-mid)*(i-mid)/stauchFaktor)+d;
            }

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if(e.getKeyChar()=='r'){
           // obj.rotate(0,Math.PI/17,0);
            object3DList.get(activeObject).rotatingY+=Math.PI/17;
        }else if(e.getKeyChar()=='t'){
           // obj.rotate(0,-Math.PI/17,0);
            object3DList.get(activeObject).rotatingY-=Math.PI/17;
        }else if(e.getKeyChar()=='f'){
            //obj.rotate(0,0,Math.PI/17);
            object3DList.get(activeObject).rotatingZ+=Math.PI/17;
        }else if(e.getKeyChar()=='g'){
            //obj.rotate(0,0,-Math.PI/17);
           // object3DList.get(activeObject).rotatingZ-=Math.PI/17;
            for(int i=0;i<width;i++){
                dftReell[i]=0;
                dftKomplex[i]=0;
                Ak[i]=0;
            }
            for(int k=0;k<width;k++){
            for(int i=0;i<width;i++){
                dftReell[k]+=Math.cos(2*Math.PI*k*i/width)*myFunction[i];
                dftKomplex[k]+=Math.sin(2*Math.PI*i*k/width)*myFunction[i];


            }}


            for(int n=0;n<width;n++) {

               for(int k =0;k<width;k++) {
                    Ak[k] += dftReell[n] * Math.cos(k * 2 * Math.PI * n / myFunction.length);
                    Ak[k] += dftKomplex[n] * Math.sin(k * 2 * Math.PI * n / myFunction.length);
                }

            }
            drawDFT = true;
            for(int i=0;i<width;i++){
                Ak[i]=Ak[i]/width;
                System.out.println(Ak[i]);
            }
            //System.out.println(sumReell+" "+sumKomplex);





                /*int n = width;
                for (int k = 0; k < n; k++) {  // For each output element
                    double sumreal = 0;
                    double sumimag = 0;
                    for (int t = 0; t < n; t++) {  // For each input element
                        double angle = 2 * Math.PI * t * k / n;
                        sumreal +=  myFunction[t] * Math.cos(angle); //+ inimag[t] * Math.sin(angle);
                        sumimag += -myFunction[t] * Math.sin(angle);
                    }
                    dftReell[k] = sumreal;
                    dftKomplex[k] = sumimag;
                    drawDFT = true;
            }*/
        }else if(e.getKeyChar()=='x'){
            object3DList.get(activeObject).scale(0.9,0.9,0.9);
        }else if(e.getKeyChar()=='c'){
            object3DList.get(activeObject).scale(1.1,1.1,1.1);
        }else if(e.getKeyChar()=='a'){
           // obj.translate(-10,0,0);
            object3DList.get(activeObject).movingX-=0.01;
        }else if(e.getKeyChar()=='d'){
            //obj.translate(10,0,0);
            object3DList.get(activeObject).movingX+=0.01;
        }else if(e.getKeyChar()=='w'){
           // obj.translate(0,-10,0);
            object3DList.get(activeObject).movingY-=0.01;
        }else if(e.getKeyChar()=='s'){
            //obj.translate(0,10,0);
            object3DList.get(activeObject).movingY+=0.01;
        }else if(e.getKeyChar()=='q'){
            //obj.translate(0,10,0);
            object3DList.get(activeObject).movingZ-=0.1;
        }else if(e.getKeyChar()=='e'){
            //obj.translate(0,10,0);
            object3DList.get(activeObject).movingZ+=0.1;
        }else if(e.getKeyChar()=='h'){
            //obj.translate(0,10,0);
            object3DList.get(object3DList.size()-1).movingZ=0;
            object3DList.get(object3DList.size()-1).movingX=0;
            object3DList.get(object3DList.size()-1).movingY=0;
            object3DList.get(object3DList.size()-1).rotatingX=0;
            object3DList.get(object3DList.size()-1).rotatingY=0;
            object3DList.get(object3DList.size()-1).rotatingZ=0;
            for(Vertex v:object3DList.get(object3DList.size()-1).vL){
                System.out.println(v.getZ()+" "+v.getTz());
            }
            addRayBeam();

        }
        else if(e.getKeyChar()=='b'){
            //obj.translate(0,10,0);
            for(Object3D obj:object3DList){
                obj.wire = true;
            }
        }else if(e.getKeyChar()=='n'){
            //obj.translate(0,10,0);
            for(Object3D obj:object3DList){
                obj.wire = false;
            }
        }else if(e.getKeyChar()=='m'){
            for(int i = 0;i<100;i++) {
                addObject();
                object3DList.get(object3DList.size() - 1).setNewPosition(0, 0, 10);
                object3DList.get(object3DList.size() - 1).movingX = (Math.random() - 0.5) * 1;
                object3DList.get(object3DList.size() - 1).movingY = (Math.random() - 0.5) * 1;
                object3DList.get(object3DList.size() - 1).movingZ = (Math.random() - 0.5) * 1;
                object3DList.get(object3DList.size() - 1).scale(Math.random()*2, Math.random()*2, Math.random()*2);
                object3DList.get(object3DList.size() - 1).rotatingY = Math.PI / (50 * Math.random());
                object3DList.get(object3DList.size() - 1).rotatingZ = Math.PI / (50 * Math.random());
            }
        }else if(e.getKeyChar()=='y'){
            addObject();
        }else if(e.getKeyChar()=='<'){
            activeObject+=1;
            if (activeObject == object3DList.size()){
                activeObject=0;
            }

        }
        if(e.getKeyChar()=='o'){
            stauchFaktor-=1;
        }
        if(e.getKeyChar()=='p'){
            stauchFaktor+=1;
        }
        if(e.getKeyChar()=='u'){
            zugKraft-=1;
        }
        if(e.getKeyChar()=='i'){
            zugKraft+=1;
        }
        else if(e.getKeyChar()=='1'||e.getKeyChar()=='2'||e.getKeyChar()=='3'
                ||e.getKeyChar()=='4'||e.getKeyChar()=='5'||e.getKeyChar()=='6'
                ||e.getKeyChar()=='7'||e.getKeyChar()=='8'||e.getKeyChar()=='9'){
            Graphics2D g2d = (Graphics2D) bitMap.createGraphics();
            if(e.getKeyChar()=='1'){
                drawColor = new Color((drawColor.getRed()+10)%255,drawColor.getGreen(),drawColor.getBlue());
            }
            if(e.getKeyChar()=='2'){
                drawColor = new Color(drawColor.getRed(),(drawColor.getGreen()+10)%255,drawColor.getBlue());
            }
            if(e.getKeyChar()=='3'){
                drawColor = new Color(drawColor.getRed(),drawColor.getGreen(),(drawColor.getBlue()+10)%255);
            }
            if(e.getKeyChar()=='4'){
                symmetricParts*=2;
            }
            if(e.getKeyChar()=='5'&&symmetricParts>1){
                symmetricParts/=2;
            }
            if(e.getKeyChar()=='6'){
                rotation+=1;
                if(rotation>6)rotation=0;
                if(rotation==1)rotation=2;
                if(rotation==5)rotation=6;
            }

            g2d.setColor(drawColor);
            g2d.fillRect(10,50,50,50);
            Color r = new Color(drawColor.getRed(),0,0);
            g2d.setColor(r);
            g2d.fillRect(10,100,20,20);
            r = new Color(0,drawColor.getGreen(),0);
            g2d.setColor(r);
            g2d.fillRect(10,120,20,20);
            r = new Color(0,0,drawColor.getBlue());
            g2d.setColor(r);
            g2d.fillRect(10,140,20,20);
            g2d.setColor(Color.GREEN);
            g2d.drawString("Parts: "+symmetricParts,10,180);
            g2d.drawString("Rotation: "+rotation+"-zählig",10,200);

        }

        //repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        rootObject.notSelected();
        TreePath selectedPath = tree.getSelectionPath();
        selectedNode = (VectorObject) selectedPath.getLastPathComponent();
        selectedNode.isSelected();
        //jSP.repaint();
        this.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* TreePath selectedPath = tree.getSelectionPath();
        selectedNode = (VectorObject) selectedPath.getLastPathComponent();
        if(((JMenuItem)e.getSource()).equals(circleMenuItem))
            currentVectorType = "Circle";
        else if(((JMenuItem)e.getSource()).equals(lineMenuItem))
            //currentVectorType = "Line";
            //addObject();
        else if(((JMenuItem)e.getSource()).equals(rectMenuItem))
            currentVectorType = "Rect";
        else if(((JMenuItem)e.getSource()).equals(translateMenuItem)) {
            currentTransformType = "Translate";

            selectedNode.affineTranslate(30,30);


        }
        else if(((JMenuItem)e.getSource()).equals(scaleMenuItem)) {
            currentTransformType = "Scale";

           selectedNode.affineScale(1.1,1.1);

        }
        else if(((JMenuItem)e.getSource()).equals(rotateMenuItem)) {
            currentTransformType = "Rotate";

            selectedNode.affineRotate(Math.PI/16);

        }
*/
        //repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        /*Graphics2D g2d = (Graphics2D) bitMap.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillOval(e.getX(),e.getY(),3,3);*/
        if(e.getY()<myFunction[e.getX()]){
            int d = (myFunction[e.getX()]-e.getY())/zugKraft;
            int mid = e.getX();
            int x1 = -(int)Math.sqrt(d*stauchFaktor)+mid;
            int x2 = (int)Math.sqrt(d*stauchFaktor)+mid;
            for(int i = x1;i<=x2;i++){
                myFunction[i]-= -((i-mid)*(i-mid)/stauchFaktor)+d;
            }

        }else{
            int d = (e.getY()-myFunction[e.getX()])/zugKraft;
            int mid = e.getX();
            int x1 = -(int)Math.sqrt(d*stauchFaktor)+mid;
            int x2 = (int)Math.sqrt(d*stauchFaktor)+mid;
            for(int i = x1;i<=x2;i++){
                myFunction[i]+= -((i-mid)*(i-mid)/stauchFaktor)+d;
            }

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(Object3D obj:object3DList){
            obj.lightSource.setX((((double)(e.getX())-width/2)*2/width));
            obj.lightSource.setY((((double)(e.getY())-height/2)*2/height)*-1);
            obj.lightSource.setZ(-0.1);
            obj.setLightSourceScalar();
            obj.lightColor = Color.YELLOW;
            // System.out.println(obj.lightSource.getX()+" "+obj.lightSource.getY()+" "+obj.lightSource.getZ());

            /*Graphics2D g2d = (Graphics2D) bitMap.createGraphics();
            g2d.setColor(Color.RED);
            g2d.setColor(drawColor);
            g2d.fillOval(e.getX(),e.getY(),5,5);
            AffineTransform at=new AffineTransform();
           // at.translate(width/2,height/2);
            //at.setToRotation(Math.PI/2);
           // g2d.transform(at);
            g2d.translate(10,10);
            g2d.rotate(Math.PI/4);
            g2d.setColor(Color.BLUE);
            g2d.fillOval(e.getX(),e.getY(),5,5);
            g2d.translate(10,-10);
            g2d.rotate(-Math.PI/4);
            g2d.setColor(Color.GREEN);
            g2d.fillOval(e.getX(),e.getY(),5,5);*/
        }

    }
}
