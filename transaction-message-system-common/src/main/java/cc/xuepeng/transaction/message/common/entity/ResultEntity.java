package cc.xuepeng.transaction.message.common.entity;

import cc.xuepeng.transaction.message.common.enums.ResultStatus;

/**
 * 返回信息的实体类。
 */
public final class ResultEntity {

    /**
     * 返回状态。
     */
    private final ResultStatus status;
    /**
     * 返回消息。
     */
    private final String msg;
    /**
     * 返回数据。
     */
    private final Object data;

    /**
     * 构造函数。
     *
     * @param builder 建造者。
     */
    private ResultEntity(Builder builder) {
        this.status = builder.status;
        this.msg = builder.msg;
        this.data = builder.data;
    }

    /**
     * WebAppResult的构造器。
     *
     * @author xuepeng
     */
    public static class Builder {
        /**
         * 返回状态。
         */
        private final ResultStatus status;
        /**
         * 返回消息。
         */
        private String msg;
        /**
         * 返回数据。
         */
        private Object data;

        /**
         * 构造函数。
         *
         * @param status 返回状态。
         */
        public Builder(ResultStatus status) {
            this.status = status;
        }

        /**
         * 设置返回消息。
         *
         * @param msg 返回消息。
         * @return 返回数据。
         */
        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        /**
         * 设置返回消息。
         *
         * @param data 返回消息。
         * @return 建造者本身。
         */
        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ResultEntity build() {
            return new ResultEntity(this);
        }
    }

    /**
     * @return 获得响应状态。
     */
    public ResultStatus getStatus() {
        return status;
    }

    /**
     * @return 获得响应消息。
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return 获得响应数据。
     */
    public Object getData() {
        return data;
    }

}
