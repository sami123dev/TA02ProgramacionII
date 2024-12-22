package cl.sasenjo.ta02samuelasenjo

class ItemMenu(var nombre: String, var precio: Double)

class ItemMesa(var itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubTotal(): Double {
        return itemMenu.precio * cantidad
    }
}
class CuentaMesa(val numeroMesa: Int, var items: MutableList<ItemMesa>, var aceptaPropina: Boolean) {
    fun agregarItem(nombre: String, precio: Double, cantidad: Int) {
        val nuevoItemMenu = ItemMenu(nombre, precio)
        val nuevoItemMesa = ItemMesa(nuevoItemMenu, cantidad)
        items.add(nuevoItemMesa)
    }
    fun calcularTotal(): Double {
        val total = items.sumOf { it.calcularSubTotal() }
        if (aceptaPropina) {
        }
        return total
    }
}