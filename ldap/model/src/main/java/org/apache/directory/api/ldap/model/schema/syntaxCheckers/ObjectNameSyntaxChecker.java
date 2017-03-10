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


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.directory.api.i18n.I18n;
import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.apache.directory.api.ldap.model.schema.SyntaxChecker;
import org.apache.directory.api.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A SyntaxChecker which verifies that a name is valid for an ObjectClass
 * or an AttributeType<br><br>
 * <pre>
 * &lt;m-name&gt; = &lt;keystring&gt; <br>
 * &lt;keystring&gt; = &lt;leadkeychar&gt; *&lt;keychar&gt;<br>
 * &lt;leadkeychar&gt; = &lt;ALPHA&gt;<br>
 * &lt;keychar&gt; = &lt;ALPHA&gt; / &lt;DIGIT&gt; / &lt;HYPHEN&gt; / &lt;SEMI&gt;<br>
 * &lt;ALPHA&gt;   = %x41-5A / %x61-7A   ; "A"-"Z" / "a"-"z"<br>
 * &lt;DIGIT&gt;   = %x30 / &lt;LDIGIT       ; "0"-"9"<br>
 * &lt;LDIGIT&gt;  = %x31-39             ; "1"-"9"<br>
 * &lt;HYPHEN&gt;  = %x2D ; hyphen ("-")<br>
 * &lt;SEMI&gt;    = %x3B ; semicolon (";")<br>
 * </pre>
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@SuppressWarnings("serial")
public class ObjectNameSyntaxChecker extends SyntaxChecker
{
    /** A logger for this class */
    private static final Logger LOG = LoggerFactory.getLogger( ObjectNameSyntaxChecker.class );

    private static final String REGEXP = "^([a-zA-Z][a-zA-Z0-9-;]*)$";

    private static final Pattern PATTERN = Pattern.compile( REGEXP );
    
    /**
     * A static instance of ObjectNameSyntaxChecker
     */
    public static final ObjectNameSyntaxChecker INSTANCE = new ObjectNameSyntaxChecker();

    
    /**
     * Creates a new instance of ObjectNameSyntaxChecker.
     */
    public ObjectNameSyntaxChecker()
    {
        super( SchemaConstants.OBJECT_NAME_SYNTAX );
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidSyntax( Object value )
    {
        String strValue;

        if ( value == null )
        {
            LOG.debug( I18n.err( I18n.ERR_04488_SYNTAX_INVALID, "null" ) );
            return false;
        }

        if ( value instanceof String )
        {
            strValue = ( String ) value;
        }
        else if ( value instanceof byte[] )
        {
            strValue = Strings.utf8ToString( ( byte[] ) value );
        }
        else
        {
            strValue = value.toString();
        }

        if ( strValue.length() == 0 )
        {
            LOG.debug( I18n.err( I18n.ERR_04488_SYNTAX_INVALID, value ) );
            return false;
        }

        // Search for the '$' separator
        Matcher match = PATTERN.matcher( strValue );

        boolean result = match.matches();

        if ( result )
        {
            LOG.debug( I18n.msg( I18n.MSG_04489_SYNTAX_VALID, value ) );
        }
        else
        {
            LOG.debug( I18n.err( I18n.ERR_04488_SYNTAX_INVALID, value ) );
        }

        return result;
    }
}
