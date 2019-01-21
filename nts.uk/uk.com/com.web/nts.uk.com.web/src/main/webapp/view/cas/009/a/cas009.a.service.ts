module cas009.a {
    import ajax = nts.uk.request.ajax;

    export const fetch = {
        opt: () => ajax('com', 'ctx/sys/auth/role/get/enum/reference/range'),
        role: {
            'has': () => ajax('com', 'ctx/sys/auth/role/user/has/role/8'),
            'get': (rids: any) => ajax('com', 'ctx/sys/auth/role/find/person/role', rids)
        },
        permision: {
            save: (command: any) => ajax('com', 'ctx/com/screen/person/role/register', command),
            remove: (command: any) => ajax('com', 'ctx/com/screen/person/role/delete', command),
            person_info: (roleId: string) => ajax('com', 'ctx/pereg/functions/auth/find-with-role', roleId),
            check: (roleId: string) => ajax('com', 'ctx/com/screen/person/role/check', roleId)
        },
    };
    export function exportExcel(): JQueryPromise<any> {
        let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
        let domainType = program[1] != null ? "CAS009" + program[1] : "";
        let _params = { domainId: "RolePersonalInfor", 
                        domainType: domainType,
                        languageId: "ja", 
                        reportType: 0};
        return nts.uk.request.exportFile('/masterlist/report/print', _params);
    }
}