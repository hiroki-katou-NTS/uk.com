<template>
<div class="KdtS10Test">
    <h3>{{title | i18n}}</h3>
    <nts-text-editor v-model="data.selectedCode" name='selected code'/>
    <nts-text-editor v-model="data.selectedName" name='selected name'/>
     <nts-checkbox v-model="data.isShowNoSelectRow" v-bind:value="1">Show Unselect Row</nts-checkbox>
    <button v-click:500="openDkls10" class="btn btn-secondary btn-block">open kdls10</button>
    <div class="row"> workLocationCD:   {{data.selectedCode}} </div>
    <div class="row"> workLocationName:   {{data.selectedName}} </div>
</div>
</template>