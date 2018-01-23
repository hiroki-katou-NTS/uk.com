module cps003.a.service {
    import ajax = nts.uk.request.ajax;

    export const push = {
        data: (command: any) => ajax(``)
    }

    export const fetch = {
        person: (id: string) => ajax(`/ctx/person/${id}`),

    }
}