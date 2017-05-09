module ccg014.b.service {
    var paths: any = {
         copyTitleMenu: "sys/portal/titlemenu/copy",
    }
    
    /** Function is used to copy Title Menu */
    export function copyTitleMenu(sourceTitleMenuCD: string, targetTitleMenuCD: string, overwrite: boolean): JQueryPromise<void> {
        var data = {
            sourceTitleMenuCD: sourceTitleMenuCD,
            targetTitleMenuCD: targetTitleMenuCD,
            overwrite: overwrite
        }
        return nts.uk.request.ajax("com", paths.copyTitleMenu, data);
    }
}