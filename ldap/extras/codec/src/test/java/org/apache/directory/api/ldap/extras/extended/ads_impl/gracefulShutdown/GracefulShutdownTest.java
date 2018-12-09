/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.api.ldap.extras.extended.ads_impl.gracefulShutdown;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.apache.directory.api.asn1.DecoderException;
import org.apache.directory.api.asn1.EncoderException;
import org.apache.directory.api.asn1.ber.Asn1Decoder;
import org.apache.directory.api.asn1.util.Asn1Buffer;
import org.apache.directory.api.ldap.extras.AbstractCodecServiceTest;
import org.apache.directory.api.ldap.extras.extended.ads_impl.gracefulShutdown.GracefulShutdownContainer;
import org.apache.directory.api.ldap.extras.extended.ads_impl.gracefulShutdown.GracefulShutdownRequestDecorator;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.Concurrency;
import com.mycila.junit.concurrent.ConcurrentJunitRunner;


/**
 * Test the GracefulShutdownTest codec
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrency()
public class GracefulShutdownTest extends AbstractCodecServiceTest
{
    /**
     * Test the decoding of a GracefulShutdown
     */
    @Test
    public void testDecodeGracefulShutdownSuccess() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x08 );
        bb.put( new byte[]
            { 
                0x30, 0x06,                 // GracefulShutdown ::= SEQUENCE {
                  0x02, 0x01, 0x01,         // timeOffline INTEGER (0..720) DEFAULT 0,
                  ( byte ) 0x80, 0x01, 0x01 // delay INTEGER (0..86400) DEFAULT 0
                                            // }
            } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 1, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 1, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x08, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    /**
     * Test the decoding of a GracefulShutdown with a timeOffline only
     */
    @Test
    public void testDecodeGracefulShutdownTimeOffline() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x05 );
        bb.put( new byte[]
            { 
                0x30, 0x03,             // GracefulShutdown ::= SEQUENCE {
                  0x02, 0x01, 0x01      // timeOffline INTEGER (0..720) DEFAULT 0,
        } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 1, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 0, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x05, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    /**
     * Test the decoding of a GracefulShutdown with a delay only
     */
    @Test
    public void testDecodeGracefulShutdownDelay() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x05 );
        bb.put( new byte[]
            { 
                0x30, 0x03,                 // GracefulShutdown ::= SEQUENCE {
                  ( byte ) 0x80, 0x01, 0x01 // delay INTEGER (0..86400) DEFAULT 0
            } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 0, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 1, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x05, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    /**
     * Test the decoding of a empty GracefulShutdown
     */
    @Test
    public void testDecodeGracefulShutdownEmpty() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x02 );
        bb.put( new byte[]
            { 
                0x30, 0x00 // GracefulShutdown ::= SEQUENCE {
            } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 0, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 0, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x02, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    /**
     * Test the decoding of a GracefulShutdown with a delay above 128
     */
    @Test
    public void testDecodeGracefulShutdownDelayHigh() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x06 );
        bb.put( new byte[]
            { 
                0x30, 0x04,                                 // GracefulShutdown ::= SEQUENCE {
                  ( byte ) 0x80, 0x02, 0x01, ( byte ) 0xF4  // delay INTEGER (0..86400) DEFAULT 0
            } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 0, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 500, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x06, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    /**
     * Test the decoding of a GracefulShutdown with a delay equals 32767
     */
    @Test
    public void testDecodeGracefulShutdownDelay32767() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x06 );
        bb.put( new byte[]
            { 
                0x30, 0x04,                                 // GracefulShutdown ::= SEQUENCE {
                  ( byte ) 0x80, 0x02, 0x7F, ( byte ) 0xFF  // delay INTEGER (0..86400) DEFAULT 0
            } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 0, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 32767, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x06, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    /**
     * Test the decoding of a GracefulShutdown with a delay above 32768
     */
    @Test
    public void testDecodeGracefulShutdownDelay32768() throws DecoderException, EncoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x07 );
        bb.put( new byte[]
            { 
                0x30, 0x05,             // GracefulShutdown ::= SEQUENCE {
                                        // delay INTEGER (0..86400) DEFAULT 0
                ( byte ) 0x80, 0x03, 0x00, ( byte ) 0x80, ( byte ) 0x00 
            } );

        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );

        GracefulShutdownRequestDecorator gracefulShutdownRequest = container.getGracefulShutdownRequest();
        assertEquals( 0, gracefulShutdownRequest.getTimeOffline() );
        assertEquals( 32768, gracefulShutdownRequest.getDelay() );

        // Check the length
        assertEquals( 0x07, gracefulShutdownRequest.computeLengthInternal() );

        // Check the encoding
        ByteBuffer bb1 = gracefulShutdownRequest.encodeInternal();

        assertArrayEquals( bb.array(), bb1.array() );

        // Check the reverse decoding
        Asn1Buffer asn1Buffer = new Asn1Buffer();
        GracefulShutdownFactory factory = new GracefulShutdownFactory( codec );
        factory.encodeValue( asn1Buffer, gracefulShutdownRequest );
        assertArrayEquals( bb.array(),  asn1Buffer.getBytes().array() );
    }


    // Defensive tests

    /**
     * Test the decoding of a GracefulShutdown with a timeOffline off limit
     */
    @Test( expected=DecoderException.class )
    public void testDecodeGracefulShutdownTimeOfflineOffLimit() throws DecoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x06 );
        bb.put( new byte[]
            { 
                0x30, 0x04,                         // GracefulShutdown ::= SEQUENCE {
                  0x02, 0x02, 0x03, ( byte ) 0xE8   // timeOffline INTEGER (0..720) DEFAULT 0,
            } );
        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );
    }


    /**
     * Test the decoding of a GracefulShutdown with a delay off limit
     */
    @Test( expected=DecoderException.class )
    public void testDecodeGracefulShutdownDelayOffLimit() throws DecoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x0b );
        bb.put( new byte[]
            { 
                0x30, 0x05,                     // GracefulShutdown ::= SEQUENCE {
                                                // delay INTEGER (0..86400) DEFAULT 0
                  ( byte ) 0x80, 0x03, 0x01, ( byte ) 0x86, ( byte ) 0xA0 
            } );
        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );
    }


    /**
     * Test the decoding of a GracefulShutdown with an empty TimeOffline
     */
    @Test( expected=DecoderException.class )
    public void testDecodeGracefulShutdownTimeOfflineEmpty() throws DecoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x0b );
        bb.put( new byte[]
            { 
                0x30, 0x02,         // GracefulShutdown ::= SEQUENCE {
                  0x02, 0x00        // timeOffline INTEGER (0..720) DEFAULT 0,
        } );
        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );
    }


    /**
     * Test the decoding of a GracefulShutdown with an empty delay
     */
    @Test( expected=DecoderException.class )
    public void testDecodeGracefulShutdownDelayEmpty() throws DecoderException
    {
        Asn1Decoder decoder = new Asn1Decoder();
        ByteBuffer bb = ByteBuffer.allocate( 0x0b );
        bb.put( new byte[]
            { 
                0x30, 0x02,                 // GracefulShutdown ::= SEQUENCE {
                  ( byte ) 0x80, 0x00       // delay INTEGER (0..86400) DEFAULT 0
            } );
        bb.flip();

        GracefulShutdownContainer container = new GracefulShutdownContainer();

        decoder.decode( bb, container );
    }
}
