@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

import ncurses.*
import platform.osx.lines_of_memory
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

    //println("Hello, World! From PID: ${getpid()}")
    val items = 20
    val rng = Random(123)

    // use random values
    //val values = Array(items) { rng.nextInt(1, items) }

    // use shuffled values
    val values = Array(items) {it + 1}
        .toList()
        .shuffled(rng)
        .toTypedArray()

    // Set locale for ncurses wide character (UTF-8) support.
    // setlocale, Passing "" results in getting the configured/system locale,
    // which should be UTF-8 based on any modern system.
    // Note: The call to setlocale must precede initscr().
    setlocale(LC_ALL, "")

    initscr()

    val window = newwin(24, 84, 0, 0)
    box(window, 0 ,0)

    fun drawGraph(array: Array<Int>, activeValue: Int){

        // Lines
        for (i in 1..(array.max()!! * 2)){
            var line = ""
            var row = 0
            // Rows
            for (j in array.indices){
                row = j
                line += if (array[j] >= i){
                    if (activeValue == j) {
                        "░ "
                    } else {
                        "█ "
                    }
                }else{
                    "  "
                }
            }
            // draw line
            mvwprintw(window, (array.max()!! - (i-2)), row, line)

            wrefresh(window)
        }

    }

    fun bubblesort(array: Array<Int>){
        for (pass in 0 until (array.size - 1)) {
            for (currentPosition in 0 until (array.size - pass - 1)) {
                if (array[currentPosition] > array[currentPosition + 1]) {
                    val tmp = array[currentPosition]
                    array[currentPosition] = array[currentPosition + 1]
                    array[currentPosition + 1] = tmp
                    drawGraph(array, currentPosition + 1)
                    usleep(100000)
                }
            }
        }
    }

    // draw on the screen
//    mvwprintw(window, 1, 1, "X")
//    mvwprintw(window, 1, 40, "X")
//    mvwprintw(window, 20, 1, "X")
//    mvwprintw(window, 20, 40, "X")

    bubblesort(values)

    wrefresh(window)


    wgetch(window) // wait for input, for now.

    // end
    delwin(window)
    endwin()
}
