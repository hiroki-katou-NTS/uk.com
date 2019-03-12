import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/about/me',
    style: require('./style.scss'),
    template: require('./index.html')
})
export class AboutComponent extends Vue {
    
}