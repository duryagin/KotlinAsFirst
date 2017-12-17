@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
        if (height > 0 && width > 0) MatrixImpl(height, width, e)
        else throw IllegalArgumentException()

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    val listOfLists = MutableList(height) {MutableList(width) {e}}

    override fun get(row: Int, column: Int): E =
            if (row in 0 until height && column in 0 until width &&
                    listOfLists[row][column].toString().isNotEmpty())
                listOfLists[row][column]
            else throw IllegalArgumentException()

    override fun get(cell: Cell): E  = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E)  =
            if (row in 0 until height && column in 0 until width)
                listOfLists[row][column] = value
            else throw IllegalArgumentException()

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) : Boolean {
        if (!(other is MatrixImpl<*> &&
                height == other.height &&
                width == other.width))
            return false
        for (row in 0 until height) {
            for (column in 0 until width)
                if (listOfLists[row][column] != other[row, column]) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + listOfLists.hashCode()
        return result
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("[")
        for (row in 0 until  height) {
            stringBuilder.append("[")
            for (column in 0 until  width - 1) {
                stringBuilder.append(this[row, column], ", ")
            }
            stringBuilder.append(this[row, width - 1])
            stringBuilder.append("], ")
        }
        stringBuilder.delete(stringBuilder.length - 2, stringBuilder.length)
        stringBuilder.append("]")
        return stringBuilder.toString()
    }
}

