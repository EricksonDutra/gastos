package ericks.devs.gastos;

import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalizarActivity extends AppCompatActivity {

    private Button mButtonCadastrar;
    private EditText mEditTextNomeFantasia;

    private View.OnClickListener mButtonCadastrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Recupera o valor digitado pelo usuário
            String nomeFantasia = mEditTextNomeFantasia.getText().toString();

            // Verifica se o valor não está vazio
            if (!nomeFantasia.isEmpty()) {

                // Acessa o SharedPreferences e armazena o valor digitado pelo usuário associado à chave "nomeFantasia"

                // Observe que a constante criada na MainActivity é fornecida no primeiro parâmetro, portanto é utilizado o mesmo arquivo para o SharedPreferences
                getSharedPreferences(MainActivity.ARQUIVO_DE_GRAVACAO,Context.MODE_PRIVATE)
                        .edit()
                        .putString("nomeFantasia", nomeFantasia)
                        .apply();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizar);

        mEditTextNomeFantasia = findViewById(R.id.editTextNomeFantasia);

        mButtonCadastrar = findViewById(R.id.buttonCadastrar);
        mButtonCadastrar.setOnClickListener(mButtonCadastrarListener);
    }
}