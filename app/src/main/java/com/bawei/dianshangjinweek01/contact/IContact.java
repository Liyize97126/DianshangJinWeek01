package com.bawei.dianshangjinweek01.contact;

/**
 * 契约类
 */
public interface IContact {
    interface IModel {
        void modelSuccess(String json);
        void modelFail(String err);
    }
    interface IView {
        void viewSuccess(String json);
        void viewFail(String err);
    }
}
