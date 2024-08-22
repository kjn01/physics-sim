import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Particles extends JPanel {

    private Particle[] particles;
    private Color[] particle_colors;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Particles(int num_particles) {
        
        particles = new Particle[num_particles];
        particle_colors = new Color[num_particles];

        for (int i = 0; i < particle_colors.length; i++) {
            particle_colors[i] = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
        }
        
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle();
            particles[i].applyAcceleration(Constants.gravity);
            particles[i].setMass(1);
            particles[i].setRadius(10);
            particles[i].setPosition(new Vector2D((int)(Math.random() * (screenSize.getWidth()) - 200) + 100, particles[i].getPosition().getY() - 10));
        }

        Timer timer = new Timer(10, new ActionListener() {

           @Override
           public void actionPerformed(ActionEvent e) {

                repaint();

           } 

        });

        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);


        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.fillRect(110, 0, (int)screenSize.getWidth() - 210, (int)screenSize.getHeight() - 100);

        int i = 0;

        for (Particle p : particles) {

            g2D.setColor(particle_colors[i]);
            // System.out.println(-(int)p.getPosition().getY());
            
            for (Particle p2 : particles) {
                if (p != p2) {
                    Vector2D[] collision = p.checkParticleCollision(p2);
                    if (collision != null) {
                        p.setVelocity(collision[0]);
                        p2.setVelocity(collision[1]);
                        p.setPosition(collision[2]);
                        p2.setPosition(collision[3]);
                    }
                }
            }

            p.update(0.01, new double[]{screenSize.getWidth() - 100, screenSize.getHeight() - 100});
            g2D.fillOval(
                (int)p.getPosition().getX(),
                -(int)p.getPosition().getY(),
                (int)p.getRadius(),
                (int)p.getRadius()
            );

            i++;

        }

    }

}
