package io.lhysin.domain.gpt.model.type

enum class GptModel(
    val modelName: String,
    val baseProvider: GptProvider,
    val bestProviders: List<GptProvider>
) {
    GPT_35_LONG("gpt-3.5-turbo", GptProvider.OPENAI_CHAT, listOf(GptProvider.FREE_GPT)),
    GPT_35_TURBO("gpt-3.5-turbo", GptProvider.OPENAI_CHAT, listOf(GptProvider.FREE_GPT, GptProvider.YOU, GptProvider.CHATGPT_NEXT, GptProvider.KOALA, GptProvider.OPENAI_CHAT, GptProvider.AICHATOS, GptProvider.CNOTE, GptProvider.FEEDOUGH)),
    GPT_4("gpt-4", GptProvider.OPENAI_CHAT, listOf(GptProvider.BING, GptProvider.LIAOBOTS)),
    GPT_4O("gpt-4o", GptProvider.OPENAI_CHAT, listOf(GptProvider.YOU, GptProvider.LIAOBOTS)),
    GPT_4_TURBO("gpt-4-turbo", GptProvider.BING, listOf()),
    GIGACHAT("GigaChat:latest", GptProvider.GIGACHAT, listOf()),
    META("meta", GptProvider.META_AI, listOf()),
    LLAMA3_8B_INSTRUCT("meta-llama/Meta-Llama-3-8B-Instruct", GptProvider.REPLICATE, listOf()),
    LLAMA3_70B_INSTRUCT("meta-llama/Meta-Llama-3-70B-Instruct", GptProvider.REPLICATE, listOf()),
    CODELLAMA_34B_INSTRUCT("codellama/CodeLlama-34b-Instruct-hf", GptProvider.HUGGING_CHAT, listOf()),
    CODELLAMA_70B_INSTRUCT("codellama/CodeLlama-70b-Instruct-hf", GptProvider.REPLICATE, listOf(GptProvider.PERPLEXITY_LABS)),
    MIXTRAL_8X7B("mistralai/Mixtral-8x7B-Instruct-v0.1", GptProvider.REPLICATE, listOf(GptProvider.PERPLEXITY_LABS)),
    MISTRAL_7B("mistralai/Mistral-7B-Instruct-v0.1", GptProvider.HUGGING_CHAT, listOf(GptProvider.HUGGING_FACE, GptProvider.PERPLEXITY_LABS)),
    MISTRAL_7B_V02("mistralai/Mistral-7B-Instruct-v0.2", GptProvider.REPLICATE, listOf(GptProvider.PERPLEXITY_LABS)),
    GEMINI("gemini", GptProvider.GEMINI, listOf()),
    CLAUDE_V2("claude-v2", GptProvider.VERCEL, listOf()),
    CLAUDE_3_OPUS("claude-3-opus", GptProvider.YOU, listOf()),
    CLAUDE_3_SONNET("claude-3-sonnet", GptProvider.YOU, listOf()),
    CLAUDE_3_HAIKU("claude-3-haiku", GptProvider.DUCKDUCKGO, listOf()),
    GPT_35_TURBO_16K("gpt-3.5-turbo-16k", GptProvider.OPENAI_CHAT, listOf(GptProvider.FREE_GPT)),
    GPT_35_TURBO_16K_0613("gpt-3.5-turbo-16k-0613", GptProvider.OPENAI_CHAT, listOf(GptProvider.FREE_GPT)),
    GPT_35_TURBO_0613("gpt-3.5-turbo-0613", GptProvider.OPENAI_CHAT, listOf(GptProvider.FREE_GPT, GptProvider.YOU, GptProvider.CHATGPT_NEXT, GptProvider.KOALA, GptProvider.OPENAI_CHAT, GptProvider.AICHATOS, GptProvider.CNOTE, GptProvider.FEEDOUGH)),
    GPT_4_0613("gpt-4-0613", GptProvider.BING, listOf()),
    GPT_4_32K("gpt-4-32k", GptProvider.BING, listOf()),
    GPT_4_32K_0613("gpt-4-32k-0613", GptProvider.BING, listOf()),
    GEMINI_PRO("gemini-pro", GptProvider.GEMINI_PRO, listOf(GptProvider.YOU)),
    PI("pi", GptProvider.PI, listOf()),
    DBRX_INSTRUCT("databricks/dbrx-instruct", GptProvider.PERPLEXITY_LABS, listOf()),
    COMMAND_R_PLUS("CohereForAI/c4ai-command-r-plus", GptProvider.HUGGING_CHAT, listOf()),
    BLACKBOX("blackbox", GptProvider.BLACKBOX, listOf()),
    REKA_CORE("reka-core", GptProvider.REKA, listOf())
}