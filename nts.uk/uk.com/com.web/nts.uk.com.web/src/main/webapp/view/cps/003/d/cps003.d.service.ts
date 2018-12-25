module cps003.d.service {
    import ajax = nts.uk.request.ajax;

    export const push = {
        data: (command: any) => ajax('', command)
    }

    export const fetch = {
        setting: (cid: string) => ajax(`ctx/pereg/grid-layout/get-setting/${cid}`)
    }
}