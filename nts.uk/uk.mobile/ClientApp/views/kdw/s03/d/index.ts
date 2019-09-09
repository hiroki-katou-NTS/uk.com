import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kdws03d',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03DComponent extends Vue {
    @Prop({ default: () => ({ employeeName: '', date: new Date(), data: {}, contentType: {} }) })
    public readonly params!: { employeeName: '', date: Date };
    public title: string = 'KdwS03D';
}