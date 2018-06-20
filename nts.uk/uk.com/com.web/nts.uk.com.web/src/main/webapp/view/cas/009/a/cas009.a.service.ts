module cas009.a {
    import ajax = nts.uk.request.ajax;

    export const fetch = {
        opt: ajax('com', 'ctx/sys/auth/role/get/enum/reference/range'),
        role: {
            'has': ajax('com', 'ctx/sys/auth/role/user/has/role'),
            'get': (rids: any) => ajax('com', 'ctx/sys/auth/role/find/person/role', rids),
            'save': (query: any) => ajax('com', 'ctx/sys/auth/role/save/person/infor', query),
            'remove': (query: any) => ajax('com', 'ctx/sys/auth/role/remove/person/infor', query),
        },
        permision: {
            save: (command: any) => ajax('com', 'ctx/pereg/functions/auth/register', command),
            remove: (command: any) => ajax('com', 'ctx/pereg/functions/auth/delete', command)
        },
    };
}