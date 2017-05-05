module qpp021.i.service {

    var paths = {
        findAllEmp: "basic/organization/employment/findallemployments",
        findAllSetting: "ctx/pr/report/payment/contact/personalsetting/findall",
        save: "ctx/pr/report/payment/contact/personalsetting/save"
    };

    export function findAllEmp(): JQueryPromise<EmployeeDto> {
        return nts.uk.request.ajax("com", paths.findAllEmp);
    }

    export function findAllSetting(): JQueryPromise<ContactPersonalSettingDto> {
        return nts.uk.request.ajax(paths.findAllSetting);
    }

    export function save(dto): void {
        return nts.uk.request.ajax(paths.save, {listContactPersonalSetting: dto});
    }

    export interface EmployeeDto {
        employmentCode: string;
        employmentName: string;
        processingNo: number;
    }

    export interface ContactPersonalSettingDto {
        employeeId: string;
        comment: string;
    }
}
