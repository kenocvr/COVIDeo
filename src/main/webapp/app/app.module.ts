import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CoviDeoSharedModule } from 'app/shared/shared.module';
import { CoviDeoCoreModule } from 'app/core/core.module';
import { CoviDeoAppRoutingModule } from './app-routing.module';
import { CoviDeoHomeModule } from './home/home.module';
import { CoviDeoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { SafePipe } from './safe.pipe';

@NgModule({
  imports: [
    BrowserModule,
    CoviDeoSharedModule,
    CoviDeoCoreModule,
    CoviDeoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CoviDeoEntityModule,
    CoviDeoAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent, SafePipe],
  bootstrap: [MainComponent]
})
export class CoviDeoAppModule {}
