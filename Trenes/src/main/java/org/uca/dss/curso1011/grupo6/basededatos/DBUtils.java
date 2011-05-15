/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.trenes.basededatos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import java.io.File;

/**
 *
 * @author dmolina
 */
public class DBUtils {
    static String fname="";
    static ObjectContainer db=null;
        
    public static void initDataBase(String fname) {
        DBUtils.fname = fname;

        if (db != null) {
            db.close();
        }
        try {
        new File(fname).delete();
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(2);
        configuration.common().updateDepth(2);
        db = Db4oEmbedded.openFile(configuration, fname);
        db.commit();
        } catch (RuntimeException e) {
            System.err.println("Mensaje : " +e.getMessage());
            throw e;
        }
    }

    public static void clear() {
        db.close();
        new File(fname).delete();
    }

    public static ObjectContainer getDb() {
        if (db == null) {
            throw new RuntimeException("Error, base de Datos no iniciada");
        }

        return db;
    }
}
