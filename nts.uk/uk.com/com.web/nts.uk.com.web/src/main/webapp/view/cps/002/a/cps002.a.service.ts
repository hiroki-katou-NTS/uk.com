module cps002.a.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import block = nts.uk.ui.block;

    let regpath = "ctx/pereg/",
        paths: any = {
            getEmployeeCode: 'employee/mngdata/getGenerateEmplCode',
            getCardNumber: 'employee/mngdata/getGenerateCardNo',
            getStamCardEditing: 'record/stamp/stampcardedit/find',
            getLayout: 'person/newlayout/check-new-layout',
            getAllInitValueSetting: 'person/info/setting/init/findAllHasChild',
            getSelfRoleAuth: 'roles/auth/get-self-auth',
            getUserSetting: 'usersetting/getUserSetting',
            getLastRegHistory: 'empreghistory/getLastRegHistory',
            getGenerateEmplCodeAndComId: 'addemployee/getGenerateEmplCodeAndComId',
            validateEmpInfo: 'addemployee/validateEmpInfo',
            getCopySetting: 'copysetting/setting/getCopySetting',
            getAllCopySettingItem: 'copysetting/item/getAll/{0}/{1}/{2}',
            getAllInitValueCtgSetting: 'initsetting/category/findAllBySetId/{0}',
            getAllInitValueItemSetting: 'initsetting/item/findInit',
            getLayoutByCreateType: 'layout/getByCreateType',
            addNewEmployee: 'addemployee/addNewEmployee',
            getEmployeeInfo: 'basic/organization/employee/getoffselect',
        };

    export function getLayout() {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        
        ajax(regpath + paths.getLayout)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getUserSetting() {

        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(regpath + paths.getUserSetting)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getLastRegHistory() {

        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(regpath + paths.getLastRegHistory)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();

    }

    export function getEmployeeCode(employeeLetter) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax("com", regpath + paths.getEmployeeCode, employeeLetter)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();


    }

    export function getCardNumber(cardLetter) {

        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax("com", regpath + paths.getCardNumber, cardLetter)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getStamCardEdit() {
        return nts.uk.request.ajax("at", paths.getStamCardEditing);
    }

    export function getEmployeeCodeAndComId(employeeLetter) {

        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax("com", regpath + paths.getCardNumber, employeeLetter)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function validateEmpInfo(command) {

        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax("com", regpath + paths.validateEmpInfo, command)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();

    }

    export function getCopySetting() {

        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(regpath + paths.getCopySetting)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getAllCopySettingItem(employeeId, categoryCd, baseDate) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(format(regpath + paths.getAllCopySettingItem, employeeId, categoryCd, baseDate))
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getAllInitValueSetting() {
        return ajax(regpath + paths.getAllInitValueSetting);
    }

    export function getAllInitValueCtgSetting(settingId: string) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(format(regpath + paths.getAllInitValueCtgSetting, settingId))
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();

    }

    export function getAllInitValueItemSetting(command) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(regpath + paths.getAllInitValueItemSetting, command)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getSelfRoleAuth() {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(regpath + paths.getSelfRoleAuth)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function getLayoutByCreateType(command) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax(regpath + paths.getLayoutByCreateType, command)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

    export function addNewEmployee(command) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.grayout());
        nts.uk.request.ajax(regpath + paths.addNewEmployee, command)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }
    export function getEmployeeInfo(command) {
        let dfd = $.Deferred<any>();
        let self = this;
        _.defer(() => block.invisible());
        nts.uk.request.ajax("com", paths.getEmployeeInfo, command)
            .done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                _.defer(() => block.clear());
            });
        return dfd.promise();
    }

}

