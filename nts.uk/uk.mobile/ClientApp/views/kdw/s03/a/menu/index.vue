<template>
    <div class="kdws03a">
        <ul class="list-group border-0">
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="processConfirmAll('confirm')" v-if="params.displayFormat == 0 && params.allConfirmButtonDis">{{'KDWS03_24' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="processConfirmAll('remove')" v-if="params.displayFormat == 0 && params.allConfirmButtonDis">{{'KDWS03_25' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openErrorList">{{'KDWS03_30' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03g(0)" v-if="params.displayFormat == 0 && params.restReferButtonDis">{{'KDWS03_31' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03f" v-if="params.displayFormat == 0 && params.monthActualReferButtonDis">{{'KDWS03_32' | i18n}}</button></li>
            <li class="list-group-item border-0">
                <button type="button" class="btn btn-primary w-100 btn-lg" v-on:click="openKdws03g(1)" v-if="params.displayFormat == 0 && params.timeExcessReferButtonDis">{{'KDWS03_33' | i18n}}</button></li>
        </ul>
    </div>
</template>