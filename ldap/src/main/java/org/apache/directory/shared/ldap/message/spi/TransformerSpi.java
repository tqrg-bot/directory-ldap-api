/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

/*
 * $Id$
 *
 * -- (c) LDAPd Group                                                    --
 * -- Please refer to the LICENSE.txt file in the root directory of      --
 * -- any LDAPd project for copyright and distribution information.      --
 *
 */

package org.apache.directory.shared.ldap.message.spi;


import org.apache.directory.shared.ldap.message.Message;


/**
 * Standard transformer service provider interface. Transforms demarshaled
 * containment trees of compiler generated stubs into Message implementations in
 * both directions: marshaling and demarshaling.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Revision$
 */
public interface TransformerSpi extends ProviderObject
{
    /**
     * Transforms the containment tree rooted at some compiler stub object into
     * a Message implementing object instance. Transformer method used after
     * demarshaling a PDU off the wire - which consequently generated the
     * argument.
     * 
     * @param a_obj
     *            the compiler specific root object holding the containment tree
     *            for the LDAPv3 message envelope. For example for the Snacc
     *            provider this object would be an instance of the LDAPMessage
     *            stub class.
     * @return the compiler agnostic Message implemenating object representing
     *         the containment tree held by the message envelope argument.
     * @throws ProviderException
     *             to indicate an error while attempting to transform
     *             library/compiler specific message envelope into agnostic
     *             message. Provider specific exceptions encountered while
     *             transforming can be held within this subclass of
     *             MultiException.
     */
    Message transform( Object a_obj ) throws ProviderException;


    /**
     * Transforms the agnostic Message representation of an LDAPv3 ASN.1 message
     * envelope into a compiler generated and BER lib specific stub containment
     * tree. Transformer method used before marshaling a PDU onto the wire - the
     * resultant object generated by this transform overload is the object
     * marshaled.
     * 
     * @param a_msg
     *            The Message object used to generate the compiler stub based
     *            containment tree.
     * @return the compiler stub based containment tree representing the Message
     *         transformed into the provider's format. For example for the Snacc
     *         provider this object would be an instance of the LDAPMessage stub
     *         class.
     * @throws ProviderException
     *             to indicate an error while attempting to transform
     *             library/compiler specific message envelope into agnostic
     *             message. Provider specific exceptions encountered while
     *             transforming can be held within this subclass of
     *             MultiException.
     */
    Object transform( Message a_msg ) throws ProviderException;
}
