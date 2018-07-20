module nts.uk.com.view.cmf002.x {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            startMenu: "exio/exo/exechist/startMenu"
        }

        export function startMenu(param): JQueryPromise<any> {
            return ajax('com', paths.startMenu);
        };

    }
}