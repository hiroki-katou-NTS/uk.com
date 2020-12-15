module nts.uk.at.view.kdl035.a.service {
    const paths: any = {
        initScreen: "screen/at/kdl035/init",
        associate: "screen/at/kdl035/associate"
    };

    export function initScreen(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.initScreen, input);
    }

    export function associate(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.associate, input);
    }
}