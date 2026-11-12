# Arena Cidadã — Gestão e Zeladoria de Espaços Esportivos Públicos

Aplicativo Android desenvolvido para a **Global Solution 2026 (Desafio CitizenTech)** na disciplina de **Android Kotlin Developer** (FIAP).

## Integrantes da Equipe
- **Nome Completo:** Matheus Rocha Sousa — **RM:** 559201
- **Nome Completo:** Luciano Henrique Gonçalves D'Oliveira — **RM:** 558975

---

## Problema e Público Beneficiado
Quadras e centros desportivos públicos sofrem frequentemente com problemas de manutenção (iluminação queimada, alambrados danificados, piso irregular) e com a falta de um canal simples para a comunidade registar ocorrências e acompanhar a resolução das demandas.

O **Arena Cidadã** beneficia diretamente **moradores locais, atletas amadores de fim de semana, líderes comunitários e zeladores**, permitindo registar solicitações por espaço desportivo, acompanhar prioridades e atualizar o estado de cada ocorrência.

---

## Conexão com os ODS da ONU (Agenda 2030)
- **ODS 3 — Saúde e Bem-Estar:** Ao facilitar a conservação de quadras e espaços públicos de lazer, a solução incentiva a prática regular de atividades físicas e o convívio saudável na comunidade.
- **ODS 11 — Cidades e Comunidades Sustentáveis:** Promove a zeladoria participativa e o acesso seguro, inclusivo e democrático aos espaços públicos urbanos.

---

## Funcionalidades Implementadas
1. **Tela Inicial (`StartScreen`):** Tela de boas-vindas com dois grandes blocos/atalhos interativos para acesso aos módulos de acompanhamento de demandas e gestão de colaboradores.
2. **Dashboard (`HomeScreen`):** Apresentação da proposta CitizenTech e resumo em tempo real do total de registos e de demandas atendidas.
3. **Listagem e Acompanhamento (`DemandListScreen`):**
   - Exibição dos registos em `LazyColumn` com indicação clara de estado vazio (*empty state*) e chips semânticos de cores.
   - **Ação adicional 1 (Alteração de Estado):** Alternar entre *Pendente* e *Resolvido* diretamente no registo.
   - **Ação adicional 2 (Exclusão):** Remover demandas da base de dados local.
4. **Cadastro de Demandas (`DemandFormScreen`):**
   - Formulário completo para inserção do nome da quadra, bairro, menu dropdown interativo para categoria e campo de texto de múltiplas linhas para a descrição.
   - Validação de campos obrigatórios impedindo o registo de dados em branco.
5. **Módulo de Colaboradores (`ColaboradoresScreen` & `ColaboradorFormScreen`):**
   - Registo no banco de dados de novos colaboradores (com Nome, Especialidade e Telefone).
   - Listagem interativa que filtra e exibe apenas as ocorrências *Pendentes*.
   - **Ação adicional 3 (Alocação Dinâmica):** Opção de atribuir um colaborador cadastrado (por meio de *Dialog* com lista suspensa) a uma demanda pendente, registando o responsável diretamente na tarefa.
6. **Persistência Local (`Room`):** Toda a base de dados (Demandas e Colaboradores) permanece guardada no dispositivo e relacionada.
7. **Identidade Visual e UI/UX:** Tema em Dark Mode nativo com cores primárias (Verde Esmeralda e Azul), com a tipografia global customizada (Google Font *Poppins*) incorporada no APK.

---

## Tecnologias e Arquitetura Utilizadas
- **Linguagem:** Kotlin
- **Interface:** Jetpack Compose + Material 3
- **Identidade Visual:** UI/UX com Dark Theme nativo, fontes customizadas localmente e ícones vectoriais semânticos.
- **Navegação:** Navigation Compose (`NavHost` / `NavController`)
- **Gestão de Estado:** `ViewModel` + `StateFlow` (`collectAsStateWithLifecycle`)
- **Persistência Local:** Room Database (`@Entity`, `@Dao`, `@Database` com migrações, Coroutines e Kotlin Flow)

### Estrutura de Pacotes (`mrsmatheusrocha.com.github.arenacidada`)
- `data/`: Entidades (`CourtDemandEntity`, `ColaboradorEntity`), DAOs correspondentes e `AppDatabase` com versão atualizada.
- `viewmodel/`: `ArenaViewModel` gerindo estados simultâneos de colaboradores e demandas, e `ArenaViewModelFactory`.
- `ui/`: Múltiplas telas (incluindo `StartScreen`, `ColaboradoresScreen`, `ColaboradorFormScreen`) e centralizador de navegação (`ArenaCidadaApp`).
- `ui/theme/`: Configuração avançada de tipografia e esquemas de cores.

---

## Demonstração e Evidências de Funcionamento

> Insira na pasta `/docs` as imagens abaixo:

1. **Tela de Boas-vindas (StartScreen) e Dashboard:**
   ![Start e Home](docs/print_home.png)

2. **Cadastro com Menu Dropdown, Ícones e Validações:**
   ![Cadastro de Demanda](docs/print_form.png)

3. **Listagem com Empty State e Alteração de Status (Pendente/Resolvido):**
   ![Listagem e Status](docs/print_list_status.png)
   
4. **Módulo de Colaboradores (Cadastro e Tela de Alocação de Pessoal):**
   ![Gestão de Colaboradores](docs/print_colaboradores.png)

5. **Evidência de Persistência com Room (Após fechar e reabrir a aplicação):**
   ![Persistência Room](docs/print_persisted.png)

---

## Instruções para Executar o Projeto
1. Descompacte o ficheiro `.zip` e abra a pasta `gs2-kotlin-main` no **Android Studio (Ladybug ou superior)**.
2. Aguarde a sincronização automática do Gradle (`Gradle Sync`).
3. Selecione um emulador ou dispositivo físico com **Android 8.0 (API 26)** ou superior.
4. Clique em **Run ('app')**.
