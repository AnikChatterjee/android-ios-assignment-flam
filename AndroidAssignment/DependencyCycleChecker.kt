fun hasCircularDependency(n: Int, edges: List<Pair<Int, Int>>): Boolean {
    val graph = Array(n) { mutableListOf<Int>() }
    val visited = IntArray(n) { 0 } 

    for ((from, to) in edges) {
        graph[from].add(to)
    }

    fun dfs(node: Int): Boolean {
        if (visited[node] == 1) return true 
        if (visited[node] == 2) return false 

        visited[node] = 1
        for (neighbor in graph[node]) {
            if (dfs(neighbor)) return true
        }
        visited[node] = 2
        return false
    }

    for (i in 0 until n) {
        if (dfs(i)) return true
    }

    return false
}

fun main() {
    val test1 = hasCircularDependency(4, listOf(0 to 1, 1 to 2, 2 to 3))
    println("Test 1: $test1") 

    val test2 = hasCircularDependency(4, listOf(0 to 1, 1 to 2, 2 to 0))
    println("Test 2: $test2") 

}
