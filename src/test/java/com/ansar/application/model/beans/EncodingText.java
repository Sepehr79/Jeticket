package com.ansar.application.model.beans;

import com.ansar.application.model.entity.beans.TextDecoder;
import org.junit.Assert;
import org.junit.Test;

public class EncodingText {

    @Test
    public void testEncoding(){
        String text = "4561561546556";

        Assert.assertEquals(TextDecoder.encode(text, 5), "VmtkMFUxWnJNWEpOVmxaWFZrVndUMVpyV2xaa01WSjBZMFphVGxaVVZuVlZSbEYzVUZFOVBRPT0=");
    }

    @Test
    public void testDecoding(){

        Assert.assertEquals(TextDecoder.decode("VmtkMFUxWnJNWEpOVmxaWFZrVndUMVpyV2xaa01WSjBZMFphVGxaVVZuVlZSbEYzVUZFOVBRPT0=", 5),
                "4561561546556");

    }

}
