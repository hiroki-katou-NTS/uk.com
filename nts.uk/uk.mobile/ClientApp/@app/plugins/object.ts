import { obj } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

export const tojs = {
    install(vue: VueConstructor<Vue>) {
        vue.prototype.toJS = obj.cloneObject;
        
        obj.extend(Vue, { toJS: obj.cloneObject });
    }
};