<template>
<div class="kafs07a">
    <div>
        <kafs00-a v-if="true" v-bind:params="{application: application}" /> 
    </div>
    <div>
        <kafs00-b v-if="true" v-bind:params="{
            application: application, 
            appDispInfoStartupOutput: appDispInfoStartupOutput
        }" /> 
    </div>
    <div style="border: 1px solid red">
        KAF007 content
    </div>
    <div>
        <kafs00-c v-if="true" v-bind:params="{
            application: application,
            appDispInfoStartupOutput: appDispInfoStartupOutput
        }" />    
    </div>
    <button type="button" class="btn btn-success" v-on:click="register">Register</button>
</div>
</template>