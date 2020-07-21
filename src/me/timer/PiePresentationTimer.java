package me.timer;

/*
 * Backbone copied from this site:
 * https://docs.oracle.com/javase/tutorial/uiswing/misc/trans_shaped_windows.html
 * 
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.geom.Ellipse2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import static java.util.concurrent.TimeUnit.*;

public class PiePresentationTimer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private Canvas canvas;
	private int intervall = 500;
	private int current = 0;
	private float max = 60 * 1000;
	private PiePresentationTimer ppt = this;

	public PiePresentationTimer() {
		super("ShapedWindow");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 1.0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		getContentPane().setLayout(gridBagLayout);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
			}
		});
		setAlwaysOnTop(true);
		setUndecorated(true);
		setSize(101, 101);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width - 105, 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 50 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1)
					reset();
				else if (arg0.getButton() == MouseEvent.BUTTON3) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								OptionsWindow frame = new OptionsWindow(getPpt());
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		canvas = new Canvas();
		GridBagConstraints gbc_canvas = new GridBagConstraints();
		gbc_canvas.gridx = 0;
		gbc_canvas.gridy = 0;
		panel.add(canvas, gbc_canvas);

		final Runnable beeper = new Runnable() {
			public void run() {
				current += intervall;
				current %= max;
				updateDrawing();
			}
		};
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 100, intervall, MILLISECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				beeperHandle.cancel(true);
			}
		}, 60 * 60, HOURS);
	}

	private void reset() {
		current = 0;
	}

	private void updateDrawing() {
		validate();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(255, 0, 0));
		int sector = -(int) (current * 360 / max);
		if (sector == 0)
			sector = 360;
		g.fillArc(0 - 1, 0 - 1, getWidth() + 2, getHeight() + 2, 90, sector);
		g.setColor(new Color(0, 0, 0));
		String s = "" + (int) (max / 1000);
		g.drawString(s, 50 - (s.length() / 2) * 7, 55);
	}

	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);
		if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
			System.err.println("Shaped windows are not supported");
			System.exit(0);
		}
		if (!isTranslucencySupported)
			System.out.println("Translucency is not supported, creating an opaque window");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PiePresentationTimer sw = new PiePresentationTimer();
				if (isTranslucencySupported) {
					sw.setOpacity(0.6f);
				}
				sw.setVisible(true);
			}
		});
	}

	public PiePresentationTimer getPpt() {
		return ppt;
	}

	public void setTimerDuration(long timerDuration) {
		max = timerDuration * 1000;
	}
}
