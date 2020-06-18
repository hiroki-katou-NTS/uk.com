module nts.uk.at.view.kdp001.a.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;



    let url = {
        getEmployeeStampData: 'at/record/stamp/employment_system/get_employee_stamp_data',
        confirmUseOfStampInput: 'at/record/stamp/employment_system/confirm_use_of_stamp_input',
        registerStampInput: 'at/record/stamp/employment_system/register_stamp_input',
        getSettingStampInput: 'at/record/stamp/employment_system/get_setting_stamp_input',
        getOmissionContents: 'at/record/stamp/employment_system/get_omission_contents',
        getStampToSuppress: 'at/record/stamp/employment_system/get_stamp_to_suppress'
    }

    export function getEmployeeStampData(): JQueryPromise<any> {
        return ajax("at", url.getEmployeeStampData);
    }

    export function confirmUseOfStampInput(cmd): JQueryPromise<any> {
        return ajax("at", url.confirmUseOfStampInput, cmd);
    }

    export function registerStampInput(cmd): JQueryPromise<any> {
        return ajax("at", url.registerStampInput, cmd);
    }
    export function getSettingStampInput(): JQueryPromise<any> {
        return ajax("at", url.getSettingStampInput);
    }
    export function getOmissionContents(query): JQueryPromise<any> {
        return ajax("at", url.getOmissionContents, query);
    }
    export function getStampToSuppress(): JQueryPromise<any> {
        return ajax("at", url.getStampToSuppress);
    }



}