module nts.uk.at.view.ktg031.a.service {
    import ajax = nts.uk.request.ajax;

    export function getToppage(rogerFlag: number, month: number) {
        return ajax(`sys/share/toppage/alarm/find/toppage/${rogerFlag}/${month}`);
    }
    
    export function getAllToppage(month: number) {
        return ajax(`sys/share/toppage/alarm/find/all-toppage/${month}`);
    }
    
    export function update(cmd: any): JQueryPromise<void> {
        return ajax('sys/share/toppage/alarm/update', cmd);
    }
}   