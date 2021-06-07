package ericks.devs.gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.NumberPicker;
//import android.support.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String ARQUIVO_DE_GRAVACAO = "ArquivoDeGravacao";
    private NumberPicker mNumberPicker;
    private RadioGroup mRadioGroup;
    private EditText mEditTextValor;
    private TextView mTextViewSaldo;
    private Button mButtonConfirmar, mButtonPersonalizar;

    private final NumberPicker.OnValueChangeListener mValorAlteradoListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            exibirSaldo(newVal);
        }
    };
    private View.OnClickListener mButtonConfirmarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Verifica se o EditText Valor não está em branco
            if (!mEditTextValor.getText().toString().isEmpty()) {

                // Recupera o valor digitado e o converte para float
                float valor = Float.parseFloat(mEditTextValor.getText().toString());

                // Recupera o ano selecionado
                int ano = mNumberPicker.getValue();

                // Verifica qual RadioButton está selecionado
                // Recuperamos o ID do RadioButton que está selecionado e comparamos com o ID dos RadioButtons que criamos no layout
                switch (mRadioGroup.getCheckedRadioButtonId()) {

                    // Caso o RadioButton Adicionar esteja selecionado
                    case R.id.radioAdicionar:
                        adicionarValor(ano, valor);
                        break;
                    // Caso o RadioButton Excluir esteja selecionado
                    case R.id.radioExcluir:
                        excluirValor(ano, valor);
                        break;
                }

                // Exibe o novo saldo para o usuário
                exibirSaldo(ano);
            }
            mEditTextValor.setText("");
        }
    };
    private View.OnClickListener mButtonPersonalizarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getBaseContext(), PersonalizarActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadioGroup = findViewById(R.id.radioGroup);
        mNumberPicker = findViewById(R.id.numberPicker);
        mEditTextValor = findViewById(R.id.etValor);
        mTextViewSaldo = findViewById(R.id.tvSaldo);
        mButtonConfirmar = findViewById(R.id.buttonConfirmar);
        mButtonPersonalizar = findViewById(R.id.buttonPersonalizar);


        NumberPicker mNumberPicker = findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(2015);
        mNumberPicker.setMaxValue(2030);

        mNumberPicker.setOnValueChangedListener(mValorAlteradoListener);

        // Registra o Listener para detectar toques no botão Confirmar
        mButtonConfirmar.setOnClickListener(mButtonConfirmarClickListener);

        // Registra o Listener para detectar toques no botão Personalizar
        mButtonPersonalizar.setOnClickListener(mButtonPersonalizarClickListener);

        // Ao iniciar a Activity, exibe o saldo
        exibirSaldo(mNumberPicker.getValue());
    }

    private void adicionarValor(int ano, float valor) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        // Recupera o valor associado a chave ano
        // Observe que ano é do tipo int, então é necessário realizar a conversão para String,
        // pois as chaves no SharedPreferences devem ser do tipo String
        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);

        float novoValor = valorAtual + valor;

        sharedPreferences.edit()
                .putFloat(String.valueOf(ano), novoValor)
                .apply();
    }

    private void excluirValor(int ano, float valor){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);

        float novoValor = valorAtual - valor;

        // Se for menor que 0, então atribui o valor 0, para que não seja armazenado valor negativo no SharedPreferences
        if (novoValor < 0){
            novoValor = 0;
        }

        // Atualiza o novo valor referente ao ano
        sharedPreferences.edit()
                .putFloat(String.valueOf(ano), novoValor)
                .apply();
    }

    private void exibirSaldo(int ano) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        // Recupera o valor associado a chave ano
        float saldo = sharedPreferences.getFloat(String.valueOf(ano), 0);

        // Exibe o valor formatado no padrao R$ 0,00 para o usuário
        mTextViewSaldo.setText(String.format("R$ %.2f", saldo));
    }

        @Override
        protected void onResume() {
            super.onResume();

            // Recupera o SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

            // Recupera o valor referente à chave "nomeFantasia".
            // Caso não possua nenhuma chave gravada, retorna null
            String nomeFantasia = sharedPreferences.getString("nomeFantasia", null);

            // Caso nomeFantasia seja diferente de nulo, há algum valor armazenado
            if (nomeFantasia != null) {
                // Define o título para a Activity
                setTitle(nomeFantasia);
            }
        }
}