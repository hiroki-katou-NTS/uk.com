module cps003.f.service {
    import ajax = nts.uk.request.ajax;

    export const push = {
        data: (command: any) => ajax('', command)
    }

    export const fetch = {
        setting: (cid: string) => ajax(`ctx/pereg/grid-layout/get-setting/${cid}`),
        getCbxOptions: (cmd: any) => ajax(`ctx/pereg/grid-layout/get-combobox/data`, cmd),
        getItemsById: (id: string) => ajax(`ctx/pereg/person/info/ctgItem/layout/findby/itemId/${id}`)
    }
}