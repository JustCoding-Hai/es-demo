package org.example;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatResponse;

public class ChatTest {
    public static void main(String[] args) {
        Qianfan qianfan = new Qianfan("19d19de4c3cd486ca3c7ce9a0660ff48", "803eda4f2ae0491681ddea73bb0690a0");
        ChatResponse response = qianfan.chatCompletion()
                .model("ERNIE-Bot-4") // 使用model指定预置模型
                // .endpoint("completions_pro") // 也可以使用endpoint指定任意模型 (二选一)
                .addMessage("user", "你好") // 添加用户消息 (此方法可以调用多次，以实现多轮对话的消息传递)
                .temperature(0.7) // 自定义超参数
                .execute(); // 发起请求
        System.out.println(response.getResult());
    }
}
