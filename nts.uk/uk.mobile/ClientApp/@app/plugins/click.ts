import { Vue, VueConstructor } from '@app/provider';

const click = {
    install(vue: VueConstructor<Vue>, timeClick: number) {
        vue.prototype.$click = function (func: Function, wait: number = timeClick || 500) {
            let first: boolean = true,
                lastPreventTime: number = new Date().getTime();

            return {
                click: function () {
                    let self = this,
                        currentPreventTime: number = new Date().getTime(),
                        time: number = currentPreventTime - lastPreventTime;

                    if (first || time > wait) {
                        first = false;
                        func.apply(self, arguments);
                    }

                    lastPreventTime = new Date().getTime();
                }
            };
        };
    }
}

export { click };