@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

import ncurses.*
import platform.posix.getpid

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

    println("Hello, World! ${getpid()} 2e!")

    val values = arrayOf(8,10,7,9,1,6,5,2,4,3)

    initscr()
    val window = newwin(20, 20, 0, 0)
    box(window, 0 ,0)

    // draw on the screen
    mvwprintw(window, 3, 2, "HI, Window!")

    wrefresh(window)

    wgetch(window) // wait for input, for now.

    // end
    delwin(window)
    endwin()
}

private fun drawGraph(array: Array<Int>, highestValue: Int, activeValue: Int){
    // TODO
}

private fun bubblesort(array: Array<Int>){
    // TODO
}