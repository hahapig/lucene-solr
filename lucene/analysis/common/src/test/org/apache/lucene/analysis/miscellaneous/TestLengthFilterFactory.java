package org.apache.lucene.analysis.miscellaneous;

/**
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.BaseTokenStreamFactoryTestCase;

public class TestLengthFilterFactory extends BaseTokenStreamFactoryTestCase {

  public void testPositionIncrements() throws Exception {
    Reader reader = new StringReader("foo foobar super-duper-trooper");
    TokenStream stream = new MockTokenizer(MockTokenizer.WHITESPACE, false);
    ((Tokenizer)stream).setReader(reader);
    stream = tokenFilterFactory("Length",
        "min", "4",
        "max", "10").create(stream);
    assertTokenStreamContents(stream, new String[] { "foobar" }, new int[] { 2 });
  }
  
  /** Test that bogus arguments result in exception */
  public void testBogusArguments() throws Exception {
    try {
      tokenFilterFactory("Length", 
          "min", "4", 
          "max", "5", 
          "bogusArg", "bogusValue");
      fail();
    } catch (IllegalArgumentException expected) {
      assertTrue(expected.getMessage().contains("Unknown parameters"));
    }
  }
}