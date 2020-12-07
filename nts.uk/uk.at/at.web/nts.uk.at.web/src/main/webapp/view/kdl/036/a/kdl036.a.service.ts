module nts.uk.at.view.kdl036.a.service {
    const paths: any = {
        initScreen: "screen/at/kdl036/init",
        associate: "screen/at/kdl036/associate"
    };

    export function initScreen(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.initScreen, input);
    }

    export function associate(input: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.associate, input);
    }
}