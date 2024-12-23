package cl.sasenjo.ta02samuelasenjo

import android.util.Log
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener


//class TextWatcher vacia para simplificar su implementacion mas tarde, puesto que solo necesito listener para OnTextChanged
open class SimpleTextWatcher : android.text.TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: android.text.Editable?) {}
}

class Listeners(private val activity: AppCompatActivity) {

    fun setupListeners() {
        val item1Input = activity.findViewById<EditText>(R.id.item1Input)
        val item1Sub = activity.findViewById<TextView>(R.id.item1Sub)
        val item2Input = activity.findViewById<EditText>(R.id.item2Input)
        val item2Sub = activity.findViewById<TextView>(R.id.item2Sub)
        val item1 = ItemMenu("Pastel de Choclo", 12000.0)
        val item2 = ItemMenu("Cazuela", 10000.0)
        val subTotal = activity.findViewById<TextView>(R.id.subTotalText)
        val propina = activity.findViewById<TextView>(R.id.tipText)
        val aceptaPropina = activity.findViewById<Switch>(R.id.propinaSwitch)
        val total = activity.findViewById<TextView>(R.id.totalText)

        // Función para actualizar el total
        fun updateTotal() {
            val item1SubValue = item1Sub.text.toString().removePrefix("$").replace(",", "").toDoubleOrNull() ?: 0.0
            val item2SubValue = item2Sub.text.toString().removePrefix("$").replace(",", "").toDoubleOrNull() ?: 0.0

            // Sumar los subtotales
            val subTotalValue = item1SubValue + item2SubValue
            Log.d("Debug", "item1SubValue: $item1SubValue, item2SubValue: $item2SubValue") //log para mostrar si se estan actualizando correctamente los valores

            subTotal.text = formatToCLP(subTotalValue)
            if (aceptaPropina.isChecked) {
                val propinaValue = subTotalValue * 0.1
                propina.text = formatToCLP(propinaValue)
                total.text = formatToCLP(subTotalValue + propinaValue)
            } else {
                propina.text = formatToCLP(0.0)
                total.text = formatToCLP(subTotalValue)
            }
        }

        // Configurar TextWatcher para los EditText
        fun setupTextWatcher(input: EditText, subTotal: TextView, item: ItemMenu) {
            input.addTextChangedListener { s ->
                val quantity = s.toString().toIntOrNull()
                val subTotalItem = if (quantity != null && quantity > 0) {
                    quantity * item.precio
                } else {
                    0.0
                }
                subTotal.text = formatToCLP(subTotalItem)
                updateTotal()
            }
        }

        // Inicializo los listeners
        setupTextWatcher(item1Input, item1Sub, item1)
        setupTextWatcher(item2Input, item2Sub, item2)
        aceptaPropina.setOnCheckedChangeListener { _, _ ->
            updateTotal()
        }
    }

    // Funcion para formatear los numeros como peso "$000.000.000"
    private fun formatToCLP(value: Double): String {
        return "$${"%,.0f".format(value)}"
    }
}