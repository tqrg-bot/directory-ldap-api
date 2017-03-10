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
package org.apache.directory.api.ldap.model.schema.syntaxCheckers;


import org.apache.directory.api.i18n.I18n;
import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A SyntaxChecker which verifies that a value is an Audio according to RFC 2252.
 * 
 * <pre>
 * The encoding of a value with Audio syntax is the octets of the value
 * itself, an 8KHz uncompressed encoding compatible with the SunOS 
 * 4.1.3 'play' utility. We implement it as a binary element.
 * </pre>
 * 
 * It has been removed in RFC 4517
 *  
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@SuppressWarnings("serial")
public class AudioSyntaxChecker extends BinarySyntaxChecker
{
    /** A logger for this class */
    private static final Logger LOG = LoggerFactory.getLogger( AudioSyntaxChecker.class );
    
    /**
     * A static instance of AudioSyntaxChecker
     */
    public static final AudioSyntaxChecker INSTANCE = new AudioSyntaxChecker();

    
    /**
     * Creates a new instance of AudioSyntaxChecker
     */
    public AudioSyntaxChecker()
    {
        super( SchemaConstants.AUDIO_SYNTAX );
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidSyntax( Object value )
    {
        LOG.debug( I18n.msg( I18n.MSG_04489_SYNTAX_VALID, value ) );
        
        return true;
    }
}
