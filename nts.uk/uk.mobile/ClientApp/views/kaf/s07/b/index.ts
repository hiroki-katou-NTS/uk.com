import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { CmmS45ComponentsApp2Component, AppWorkChange } from '../../../cmm/s45/components/app2/index';

@component({
    name: 'kafs07b',
    route: '/kaf/s07/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components: {
        cmms45componentsapp2: CmmS45ComponentsApp2Component
    },
    constraints: []
})
export class KafS07BComponent extends Vue {
    public title: string = 'KafS07B'; 
    public app: AppWorkChange = {
        workType: '001 worktype',
        workTime: '002 worktime',
        workHours1: '08:30 ~ 17:30',
        workHours2: '08:30 ~ 17:30',
        straight: true,
        bounce: false
    };
    
}