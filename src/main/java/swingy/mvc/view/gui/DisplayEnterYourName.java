package swingy.mvc.view.gui;

import swingy.db.DataBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DisplayEnterYourName extends JFrame {
    private SwingView swingView;
    private javax.swing.JButton Go;
    private javax.swing.JTextField nameOfHero;
    private String error;

    DisplayEnterYourName(SwingView view) {
        swingView = view;
        JLabel lname = new JLabel();
        nameOfHero = new javax.swing.JTextField();
        Go = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("swing");

        lname.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lname.setForeground(new java.awt.Color(0, 204, 0));
        lname.setLabelFor(nameOfHero);
        lname.setText("Enter Your Name :");

        nameOfHero.setBackground(new java.awt.Color(204, 204, 255));
        nameOfHero.setToolTipText("");

        Go.setBackground(new java.awt.Color(0, 0, 153));
        Go.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        Go.setForeground(new java.awt.Color(255, 51, 0));
        Go.setText("Submit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Go, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lname)
                                                .addGap(18, 18, 18)
                                                .addComponent(nameOfHero, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameOfHero, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(Go, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }

    void setListeners() {
        Go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                error = swingView.directorHero.SetTheNameOfUser(SwingView.hero, nameOfHero.getText());

                try {
                    if (DataBase.getDb().heroExists(nameOfHero.getText())) {
                        error = "Already created !!!";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (error != null) {
                    JOptionPane.showMessageDialog(swingView, error, "Notice to you", JOptionPane.ERROR_MESSAGE);
                    swingView.swingEnterYourName();
                } else {
                    try {
                        DataBase.getDb().insertHero(SwingView.hero);
                        SwingView.names.add(nameOfHero.getText());
                        JOptionPane.showMessageDialog(swingView, "Created !!!", "Notice to you", JOptionPane.INFORMATION_MESSAGE);
                        nameOfHero.setText("");
                        swingView.displayStartViewAndGetHero();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}

