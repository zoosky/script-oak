import michid.script.oak._

// Assuming a file store at ./segmentstore
implicit val fs = fileStoreAnalyser()
val superRoot = fs.getNode()
println(s"superRoot=$superRoot")

val rootNode = fs.getNode("root");
println(s"superNode=$rootNode")

// Analysing the items in this file store
import michid.script.oak.nodestore.Items._

// All nodes flat
val nodes = collectNodes(fs.getNode("root").analyse)
println(s"nodes=${nodes take 10}")

// Find all nodes with 2 child nodes
val binaryNodes = nodes.filter(_.nodes.size == 2)
println(s"binaryNodes=${binaryNodes take 10}")

// Group properties by number of values
val properties = collectProperties(fs.getNode("root").analyse)
val byPropertyCount = properties.groupBy(_.values.size)
println(s"byPropertyCount=${byPropertyCount take 10}")

// Number of nodes with a given number of properties in decreasing order
val noOfProps = byPropertyCount.mapValues(_.size).toList.sortBy(-_._2)
println(s"noOfProps=${noOfProps take 10}")

// Number values per value type
val values = collectValues(fs.getNode("root").analyse)
val valuesPerType = values.groupBy(_.tyqe).mapValues(_.size)
println(s"valuesPerType=${valuesPerType}")

// Number of bytes per value type
val bytesPerValue = values.groupBy(_.tyqe).mapValues(_.map(v => v.parent.state.size(v.index)).sum)
println(s"bytesPerValue=${bytesPerValue}")