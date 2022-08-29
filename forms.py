from rol.models import *
from django import forms


class CreateRolForm(forms.ModelForm):
    """
    Formulario para crear un Rol
    """
    class Meta:
        """
        Clase en la que se definen los datos necesarios y adicionales para inicializacion y
        visualizacion del formulario
        """
        model = Rol
        fields = [
            'nombre',
            'is_unique',
            'descripcion',
            'permisos'
        ]
        labels = {
            'nombre': 'Nombre del rol',
            'descripcion': 'Descripcion ',
            'permisos': 'Permisos',

        }
        widgets = {
            'permisos': forms.CheckboxSelectMultiple(),
        }

    def __init__(self, *args, **kwargs):
        """
        Constructor del formulario
        :param args: argumentos para inicializacion
        :param kwargs: diccionario de datos adicionales para inicializacion
        """
        super(CreateRolForm, self).__init__(*args, **kwargs)
        permisos_all = Permiso.objects.filter(tipo=2)
        #permisos_all = Permiso.objects.all()
        p = self.fields['permisos'].widget
        permisos = []
        for permiso in permisos_all:
            permisos.append((permiso.id, permiso.nombre))
        p.choices = permisos


class UpdateRolForm(forms.ModelForm):
    """
    Formulario para modificar un Rol
    """
    class Meta:
        """
        Clase en la que se definen los datos necesarios y adicionales para inicializacion y
        visualizacion del formulario
        """
        model = Rol
        fields = [
            'nombre',
            'is_unique',
            'descripcion',
            'permisos'
        ]
        labels = {
            'nombre': 'Nombre del rol',
            'descripcion': 'Descripcion ',
            'permisos': 'Permisos',

        }
        widgets = {
            'permisos': forms.CheckboxSelectMultiple(),
        }

    def __init__(self, *args, **kwargs):
        """
        Constructor del formulario
        :param args: argumentos para inicializacion
        :param kwargs: diccionario de datos adicionales para inicializacion
        """
        super(UpdateRolForm, self).__init__(*args, **kwargs)
        permisos_all = Permiso.objects.filter(tipo=2)
        #permisos_all = Permiso.objects.all()
        p = self.fields['permisos'].widget
        permisos = []
        for permiso in permisos_all:
            permisos.append((permiso.id, permiso.nombre))
        p.choices = permisos