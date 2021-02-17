package UI;

import AI.state.AIState;
import AI.state.Stand;
import AI.state.Wander;
import core.Size;
import entity.Client;
import entity.Person;
import entity.RetailShop;
import entity.Shop;
import map.JMap;
import symulator.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class InformationWindow extends JPanel {
    private Size size;
    private JMap jmap;
    private JPanel informationPanel;
    private JPanel controlPanel;
    private JPanel background;
    private String name;
    private int ID;
    private JButton changeBB;
    private JButton deleteBB;
    private JButton addBB;
    private JTextField transmitionRate;
    private UIText changeLabel;
    private UIText delLabel;
    private UIText addLabel;
    private UIText transLabel;
    private JTextField lockdownThreshold;
    private JTextField recoveryTime;
    private Person selectedPerson;
    private ControlPanel controlPan;
    private JTextField TransVaccinated;
    private JTextField transMask;


    public InformationWindow(Size size,ControlPanel controlPanel) {
        this.jmap = jmap;
        this.size = size;
        this.controlPan = controlPanel;
        new BorderLayout();
        setPreferredSize(new Dimension(size.getWidth()-10,size.getHeight()));
        setFocusable(false);
        setVisible(true);
        setBackground(Color.darkGray);

        informationPanel = new JPanel();
        informationPanel.setVisible(true);
        informationPanel.setPreferredSize(new Dimension(size.getWidth()-10,size.getHeight()/2-50));
        informationPanel.setBackground(Color.darkGray);

        add(informationPanel,BorderLayout.PAGE_START);


        background = new JPanel();
        background.setVisible(true);
        background.setPreferredSize(new Dimension(size.getWidth()-10,size.getHeight()/2+50));
        background.setBackground(Color.darkGray);
        add(background,BorderLayout.PAGE_END);
        initializeUI();
        initializeControlPanel();
        setVisible(true);

    }

    public void setControlPan(ControlPanel controlPan) {
        this.controlPan = controlPan;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
    public JMap getJmap() {
        return jmap;
    }

    public void setJmap(JMap jmap) {
        this.jmap = jmap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void repaint(JPanel background){
        Component[] componentList = background.getComponents();
        for(Component c : componentList){
            background.remove(c);
        }
        revalidate();
        initializeUI();
    }
    public void updatePersonInfo(Person person){
        setSelectedPerson(person);
        repaint(informationPanel);
        if (person instanceof Client){
            informationPanel.add( new UIText("Client",70,22,20,size));
        }else{
            informationPanel.add( new UIText("Supplier",70,22,20,size));
        }
        informationPanel.add( new UIText(String.format("Name: %s",person.getFirstName()),10,100));
        informationPanel.add( new UIText(String.format("Surname: %s",person.getLastName()),10,120));
        informationPanel.add( new UIText(String.format("Capacity: %d",person.getMaxCapacity()),10,140));
        informationPanel.add( new UIText(String.format("ID: %d",person.getID()),10,160));
    }
    public void updateShopInfo(Shop shop){
        repaint(informationPanel);
        if (shop instanceof RetailShop){
            informationPanel.add( new UIText("RetailShop",70,22,20,size));
        }else{
            informationPanel.add( new UIText("WholeSale",70,22,20,size));
        }
        informationPanel.add( new UIText(String.format("Name: %s",shop.getName()),10,100));
        informationPanel.add( new UIText(String.format("Storage: max %d",shop.getClientCapacity()),10,120));
        informationPanel.add( new UIText(String.format("Clients: max %d",shop.getStorageCapacity()),10,140));
        informationPanel.add( new UIText(String.format("ID: %d",shop.getID()),10,160));
        informationPanel.add( new UIText(String.format("Address: %s",shop.getAddress()),10,180));
    }


    private ImageIcon getSprite(Color color){
        BufferedImage image = new BufferedImage(size.getWidth(),size.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(0,0,size.getWidth(),size.getHeight());
        graphics.dispose();
        return new ImageIcon(image);
    }
    private void initializeUI(){
        UIText title = new UIText("Pandemic",0,24,24,size);
        informationPanel.add(title);
        title = new UIText("Symulator",30,24,24,size);
        informationPanel.add(title);
    }

    private void initializeControlPanel(){
        changeBB = new JButton();
        changeBB.setText("CHANGE ROUTE");
        changeBB.setFont(new Font("Arial", Font.PLAIN, 18));
        changeBB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AIState currentState = getSelectedPerson().getAiManager().getCurrentAIState();
                if ( currentState instanceof Wander){
                    getSelectedPerson().getController().stop();
                    ((Wander) currentState).setPath(new ArrayList<>());
                    getSelectedPerson().getAiManager().setCurrentAIState(new Stand());

                }
            }
        });
        deleteBB = new JButton();
        deleteBB.setText("DELETE");
        deleteBB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getSelectedPerson().deleteMe();
            }
        });
        deleteBB.setFont(new Font("Arial", Font.PLAIN, 18));
        addBB = new JButton();
        addBB.setText("ADD");
        addBB.setFont(new Font("Arial", Font.PLAIN, 18));
        addBB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Boolean bool = ThreadLocalRandom.current().nextBoolean();
                jmap.createPerson(bool);

            }
        });

        transmitionRate = new JTextField();
        transmitionRate.setPreferredSize(new Dimension(40,20));
        transmitionRate.setText(String.valueOf(controlPan.getTransmissionRate()));
        transmitionRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = transmitionRate.getText();
                transmitionRate.setText(input);
                controlPan.setTransmissionRate(Double.parseDouble(input));
            }
        });

        lockdownThreshold = new JTextField();
        lockdownThreshold.setPreferredSize(new Dimension(40,20));
        lockdownThreshold.setText(String.valueOf(controlPan.getLockdownTreshold()));
        lockdownThreshold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = lockdownThreshold.getText();
                lockdownThreshold.setText(input);
                controlPan.setLockdownTreshold(Double.parseDouble(input));
            }
        });

        recoveryTime = new JTextField();
        recoveryTime.setPreferredSize(new Dimension(40,20));
        recoveryTime.setText(String.valueOf(controlPan.getVisitsBeforeRecovery()));
        recoveryTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = recoveryTime.getText();
                recoveryTime.setText(input);
                controlPan.setVisitsBeforeRecovery(Integer.parseInt(input));
            }
        });
        TransVaccinated = new JTextField();
        TransVaccinated.setPreferredSize(new Dimension(40,20));
        TransVaccinated.setText(String.valueOf(controlPan.getTransmissionVaccinated()));
        TransVaccinated.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = TransVaccinated.getText();
                TransVaccinated.setText(input);
                controlPan.setTransmissionVaccinated(Double.parseDouble(input));
            }
        });
        transMask = new JTextField();
        transMask.setPreferredSize(new Dimension(40,20));
        transMask.setText(String.valueOf(controlPan.getTransmissionRateMas()));
        transMask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = transMask.getText();
                transMask.setText(input);
                controlPan.setTransmissionRateMas(Double.parseDouble(input));
            }
        });

        changeLabel = new UIText("Change route",0,20);
        background.add(changeLabel);
        background.add(changeBB);
        delLabel = new UIText("Delete Person",0,80);
        background.add(delLabel);
        background.add(deleteBB);
        addLabel = new UIText("Add product",0,140);
        background.add(addLabel);
        background.add(addBB);
        transLabel = new UIText("Transmission",0,200);
        background.add(transLabel);
        background.add(transmitionRate);
        transLabel = new UIText("Trans Vacc",0,200);
        background.add(transLabel);
        background.add(TransVaccinated);
        transLabel = new UIText("Trans Mask",0,200);
        background.add(transLabel);
        background.add(transMask);

        transLabel = new UIText("Threshold",0,200);
        background.add(transLabel);
        background.add(lockdownThreshold);
        transLabel = new UIText("Recovery",0,200);
        background.add(transLabel);
        background.add(recoveryTime);

    }
}
