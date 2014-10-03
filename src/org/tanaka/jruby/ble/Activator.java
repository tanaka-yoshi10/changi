package org.tanaka.jruby.ble;

import java.util.Map;

import org.jruby.RubySymbol;
import org.jruby.embed.osgi.OSGiScriptingContainer;
import org.jruby.runtime.builtin.IRubyObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.fujitsu.m2m.faultdetection.ble"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private static BundleContext m_context;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
		m_context = context;
        new Thread() {
            public void run() {
            	Activator.exec();
            }
        }.start();
	}
	
	public void notify(Map<String, String> map) {
		System.out.println("callback " + map);
		System.out.println("callback " + map.keySet().toArray()[0]);
		System.out.println("callback " + map.keySet());
		System.out.println("callback " + map.get("code"));
	}
	
	public static void exec() {
        try {
        	Bundle bundle = m_context.getBundle();
        	OSGiScriptingContainer  container = new OSGiScriptingContainer(bundle);
            Object o = container.runScriptlet("require 'ruby/notification.rb'");
            System.out.println(o.toString());
            o = container.runScriptlet("self");
            System.out.println(o.toString());
            container.callMethod(o,"hello");
            container.callMethod(o,"startNotify", plugin);            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
