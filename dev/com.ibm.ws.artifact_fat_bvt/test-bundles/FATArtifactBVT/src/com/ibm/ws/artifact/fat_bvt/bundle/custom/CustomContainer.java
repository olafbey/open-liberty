/*
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * WLP Copyright IBM Corp. 2017
 *
 * The source code for this program is not published or otherwise divested 
 * of its trade secrets, irrespective of what has been deposited with the 
 * U.S. Copyright Office.
 */
package com.ibm.ws.artifact.fat_bvt.bundle.custom;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.ibm.wsspi.artifact.ArtifactContainer;
import com.ibm.wsspi.artifact.ArtifactEntry;

/**
 * Simple custom container based on properties.
 */
public class CustomContainer implements ArtifactContainer {

    protected CustomContainer(Properties properties) {
        this.entries = new HashMap<String, ArtifactEntry>();
        this.populate(properties);
    }


    //

    @Override
    public ArtifactContainer getEnclosingContainer() {
        return null;
    }

    @Override
    public ArtifactEntry getEntryInEnclosingContainer() {
        return null;
    }

    //

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public ArtifactContainer getRoot() {
        return this;
    }

    //

    @Override
    public String getName() {
        return "/";
    }

    @Override
    public String getPath() {
        return "/";
    }

    //

    private final Map<String, ArtifactEntry> entries;

    private void populate(Properties properties) {
        for (Entry<Object, Object> e : properties.entrySet()) {
            String propertyName = e.getKey().toString();
            String propertyValue = e.getValue().toString();

            CustomEntry customEntry = new CustomEntry(this, propertyName, propertyValue);

            entries.put("/" + propertyName, customEntry);
        }
    }

    @Override
    public CustomEntry getEntry(String path) {
        if ( !path.startsWith("/") ) {
            path = "/" + path;
        }
        return (CustomEntry) entries.get(path);
    }

    @Override
    public Iterator<ArtifactEntry> iterator() {
        return entries.values().iterator();
    }

    @Override
    public void useFastMode() {
        // EMPTY
    }

    @Override
    public void stopUsingFastMode() {
        // EMPTY
    }

    //

    @Deprecated
    @Override
    public String getPhysicalPath() {
        return null;
    }

    @Override
    public Collection<URL> getURLs() {
        return Collections.<URL> emptySet();
    }

    //

    @Override
    public CustomNotifier getArtifactNotifier() {
        return CustomNotifier.INSTANCE;
    }
}