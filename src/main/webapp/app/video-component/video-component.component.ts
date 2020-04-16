import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'jhi-video-component',
  templateUrl: './video-component.component.html',
  styleUrls: ['./video-component.component.scss']
})
export class VideoComponentComponent implements OnInit {
  name = 'Set iframe source';
  url: string = 'https://meet.jit.si/OwlsEatMyLeg';
  urlSafe: SafeResourceUrl | undefined;

  constructor(public sanitizer: DomSanitizer) {}

  ngOnInit() {
    this.urlSafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
  }
}
