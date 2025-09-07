import WebViewer from "@pdftron/webviewer";

WebViewer({
  path: '/lib',
  initialDoc: '/docs/test_doc.pdf',
  disabledElements: [
    "underlineToolButton",
    "strikeoutToolButton",
    "squigglyToolButton",
    "freeHandToolButton",
    "freeHandHighlightToolButton",
    "freeTextToolButton",
    "markInsertTextToolButton",
    "markReplaceTextToolButton",
    "calloutToolButton",
    "stickyToolButton"
  ]
}, document.getElementById('viewer'))
  .then(instance => {
    const { Annotations, documentViewer, annotationManager } = instance.Core;

document.getElementById('convert-btn').addEventListener('click', async () => {
  const allAnnotations = annotationManager.getAnnotationsList();
  const highlightAnnotations = allAnnotations.filter(ann => 
    ann.elementName === 'highlight' || 
    ann.elementName === 'textHighlight'
  );

  highlightAnnotations.forEach(async (highlight,i) => {
    const firstQuad = highlight.Quads[0];
    const comment = highlight.getContents();
    if (firstQuad) {
      const sticky = new Annotations.StickyAnnotation();
      sticky.PageNumber = highlight.PageNumber;
      sticky.X = firstQuad.x1;
      sticky.Y = firstQuad.y1;  
      sticky.Width = 60;
      sticky.Height = 25;
      sticky.Author = "System";
      sticky.setContents(comment);
      
      annotationManager.addAnnotation(sticky);
      annotationManager.redrawAnnotation(sticky);
    }
  });

  const xfdfString = await annotationManager.exportAnnotations();
  const fileData = await documentViewer.getDocument().getFileData({
    xfdfString: xfdfString,
    includeAnnotations: true
  });
  uploadForConversion(new Blob([fileData]));
});
  });


async function uploadForConversion(blob) {
  const formData = new FormData();
  formData.append('file', blob, 'document.pdf');

  const response = await fetch('http://localhost:8080/api/convert', {
    method: 'POST',
    body: formData,
  });
  if (response.ok) {
    const convertedBlob = await response.blob();

    const url = window.URL.createObjectURL(convertedBlob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'converted.docx';
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
  } else {
    console.error('Conversion failed:', response.status);
  }
}