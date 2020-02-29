@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

import ncurses.*
import platform.posix.*
import kotlin.random.Random

/**
▀ ▁ ▂ ▃ ▄ ▅ ▆ ▇ █ ▉ ▊ ▋ ▌ ▍ ▎ ▏ ▐ ░ ▒ ▓ ▔ ▕ ▖ ▗ ▘ ▙ ▚ ▛ ▜ ▝ ▞ ▟

{4,2,1,1,1,2,6,4,3,2}
▐
▐
▐     ▐▐
▐     ▐▐▐
▐▐  ▐▐▐▐▐
▐▐▐▐▐▐▐▐▐▐
{4,2,1,1,1,2,6,4,3,2}

 */
fun main(args: Array<String>) {

    val items = 30              // items/values in the array to sort, aka. the rows in the graph
    val delay = 100000U         // delay per sort step in microsecond (μs)
    val rng = Random(123)  // random number generator for shuffling the array values

    // use random values
    //val values = Array(items) { rng.nextInt(1, items) }

    // use shuffled values
    var values = Array(items) { it + 1 }
        .toList()
        .shuffled(rng)
        .toTypedArray()

    // Set locale for ncurses wide character (UTF-8) support.
    // setlocale, Passing "" results in getting the configured/system locale,
    // which should be UTF-8 based on any modern system.
    // Note: The call to setlocale must precede initscr().
    setlocale(LC_ALL, "")

    initscr()

    val window = newwin(34, 96, 0, 0)
    box(window, 0, 0)

    fun drawDummyGraphBox(array: Array<Int>) {

        // Title
        mvwprintw(window, 1, 1, "Title of graph!")

        // Lines
        for (i in 0..(array.max()!!)) {
            var line = ""
            var row = 0
            // Rows
            for (j in array.indices) {
                row = j
                line += "█░"
            }
            // draw line
            mvwprintw(window, (i + 2), 2, line)

            wrefresh(window)
        }
    }

    fun drawGraph(array: Array<Int>, activeValue: Int, title: String = "") {

        // Title
        mvwprintw(window, 1, 1, title)

        // Lines
        for (i in 0..(array.max()!!)) {
            var line = ""
            var row = 0
            // Rows
            for (j in array.indices) {
                row = j
                line += if (array[j] >= i) {
                    if (activeValue == j) {
                        "░ "
                    } else {
                        "█ "
                    }
                } else {
                    "  "
                }
            }
            // draw line
            mvwprintw(window, (array.max()!! - (i - 2)), 2, line)

            wrefresh(window)
        }

    }

    fun bubblesort(array: Array<Int>, stateCallback: (array: Array<Int>, activeValue: Int) -> Unit) {
        for (pass in 0 until (array.size - 1)) {
            for (currentPosition in 0 until (array.size - pass - 1)) {
                if (array[currentPosition] > array[currentPosition + 1]) {
                    val tmp = array[currentPosition]
                    array[currentPosition] = array[currentPosition + 1]
                    array[currentPosition + 1] = tmp
                    stateCallback(array, currentPosition + 1)
                }
            }
        }
    }


    fun cocktailsort(array: Array<Int>, stateCallback: (array: Array<Int>, activeValue: Int) -> Unit) {

        fun swap(i: Int, j: Int) {
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
        }

        do {
            var swapped = false
            for (i in 0 until array.size - 1)
                if (array[i] > array[i + 1]) {
                    swap(i, i + 1)
                    swapped = true
                    stateCallback(array, i + 1)
                }
            if (!swapped) break
            swapped = false
            for (i in array.size - 2 downTo 0)
                if (array[i] > array[i + 1]) {
                    swap(i, i + 1)
                    swapped = true
                    stateCallback(array, i + 1)
                }
        } while (swapped)
    }

    bubblesort(values) { array, activeVAlue ->
        drawGraph(array, activeVAlue, "Bubble sort")
        usleep(delay)
    }

    values = values.toList()
        .shuffled(rng)
        .toTypedArray()

    cocktailsort(values) { array, activeVAlue ->
        drawGraph(array, activeVAlue, "Cocktail sort")
        usleep(delay)
    }

    // Dummy box
    // drawDummyGraphBox(values)

    wrefresh(window)

    wgetch(window) // wait for input, for now.

    // end
    delwin(window)
    endwin()
}
