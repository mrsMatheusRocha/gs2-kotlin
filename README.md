# Arena Cidadã — Gestão e Zeladoria de Espaços Esportivos Públicos

Aplicativo Android desenvolvido para a **Global Solution 2026 (Desafio CitizenTech)** na disciplina de **Android Kotlin Developer** (FIAP).

## 👨‍💻 Integrantes da Equipe
- **Nome Completo:** Matheus Rocha Sousa — **RM:** [PREENCHER RM]
- **Nome Completo:** [PREENCHER SE HOUVER] — **RM:** [PREENCHER RM]

---

## 🎯 Problema e Público Beneficiado
Quadras e centros desportivos públicos sofrem frequentemente com problemas de manutenção (iluminação queimada, alambrados danificados, piso irregular) e com a falta de um canal simples para a comunidade registar ocorrências e acompanhar a resolução das demandas.

O **Arena Cidadã** beneficia diretamente **moradores locais, atletas amadores de fim de semana, líderes comunitários e zeladores**, permitindo registar solicitações por espaço desportivo, acompanhar prioridades e atualizar o estado de cada ocorrência.

---

## 🌍 Conexão com os ODS da ONU (Agenda 2030)
- **ODS 3 — Saúde e Bem-Estar:** Ao facilitar a conservação de quadras e espaços públicos de lazer, a solução incentiva a prática regular de atividades físicas e o convívio saudável na comunidade.
- **ODS 11 — Cidades e Comunidades Sustentáveis:** Promove a zeladoria participativa e o acesso seguro, inclusivo e democrático aos espaços públicos urbanos.

---

## ⚙️ Funcionalidades Implementadas
1. **Ecrã Inicial (`HomeScreen`):** Apresentação da proposta CitizenTech, resumo em tempo real do total de registos e de demandas atendidas, e atalhos rápidos de navegação.
2. **Listagem e Acompanhamento (`DemandListScreen`):**
   - Exibição dos registos em `LazyColumn` com indicação clara de estado vazio (*empty state*).
   - **Ação adicional 1 (Alteração de Estado):** Alternar entre *Pendente* e *Resolvido* diretamente no registo.
   - **Ação adicional 2 (Exclusão):** Remover demandas da base de dados local.
3. **Cadastro com Validação (`DemandFormScreen`):**
   - Formulário para inserção do nome da quadra, bairro/região, categoria e descrição detalhada.
   - Validação de campos obrigatórios impedindo o registo de dados em branco.
4. **Persistência Local (`Room`):** Todos os dados permanecem guardados no dispositivo após fechar e reabrir a aplicação.

---

## 🛠️ Tecnologias e Arquitetura Utilizadas
- **Linguagem:** Kotlin
- **Interface:** Jetpack Compose + Material 3 (com tipografia personalizada *Poppins*)
- **Navegação:** Navigation Compose (`NavHost` / `NavController`)
- **Gestão de Estado:** `ViewModel` + `StateFlow` (`collectAsStateWithLifecycle`)
- **Persistência Local:** Room Database (`@Entity`, `@Dao`, `@Database` com Coroutines/Flow)

### Estrutura de Pacotes (`mrsmatheusrocha.com.github.arenacidada`)
- `data/`: `CourtDemandEntity`, `CourtDemandDao` e `AppDatabase`
- `viewmodel/`: `ArenaViewModel` e `ArenaViewModelFactory`
- `ui/`: `ArenaCidadaApp`, `HomeScreen`, `DemandListScreen`, `DemandFormScreen` e `theme/`

---

## 📸 Demonstração e Evidências de Funcionamento

> Substitua os caminhos abaixo pelas capturas de ecrã reais da aplicação no emulador:

1. **Ecrã Inicial e Contextualização:**
   ![Tela Inicial](docs/print_home.png)

2. **Cadastro e Validação de Campos Obrigatórios:**
   ![Cadastro de Demanda](docs/print_form.png)

3. **Listagem e Alteração de Estado (Pendente -> Resolvido):**
   ![Listagem e Status](docs/print_list_status.png)

4. **Evidência de Persistência com Room (Após fechar e reabrir a aplicação):**
   ![Persistência Room](docs/print_persisted.png)

---

## ▶️ Instruções para Executar o Projeto
1. Descompacte o ficheiro `.zip` e abra a pasta `gs2-kotlin-main` no **Android Studio (Ladybug ou superior)**.
2. Aguarde a sincronização automática do Gradle (`Gradle Sync`).
3. Selecione um emulador ou dispositivo físico com **Android 8.0 (API 26)** ou superior.
4. Clique em **Run ('app')**.
