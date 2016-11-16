/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package michid.script.oak

import ammonite.ops.Path
import org.apache.jackrabbit.oak.segment.file.{AbstractFileStore, JournalReader}
import org.apache.jackrabbit.oak.segment.{RecordId, SegmentNodeState}
import scala.collection.JavaConversions._

/**
  * Revisions from a journal file
  */
object Revisions {
  def apply(journal: Path): Iterable[String] = new Iterable[String] {
    override def iterator: Iterator[String] = new JournalReader(journal.toNIO.toFile)
  }

  def nodes(recordIds: Iterable[String], store: AbstractFileStore): Iterable[SegmentNodeState] =
    recordIds.map(rev => store.getReader.readNode(RecordId.fromString(store, rev)))
}
